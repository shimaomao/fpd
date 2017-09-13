RiskControl30_90=function(status,username,password,dbname,host,port){
  
  
  #cache space
  memory.limit(10240000)
  today<-Sys.Date()
  
  
  
  #library#
  library(DBI)
  library(RMySQL)
  library(lubridate)
  library(reshape)
  
  
  #get data#
  #connet MySQL
  con = dbConnect(
    dbDriver("MySQL"),
    user    = username,
    password= password,
    dbname  = dbname,
    host    = host,
	port = port
    #user    = "root",
    #password= "123456",
   # dbname  = "wf_wish",
    #host    = "192.168.1.2"
    #host    = "cskk.f3322.net",
    #port = 85
  )

  
  
  #time series dealing#
  all_return_date<-c(as.Date("2017-3-1")+months(1:800),as.Date("2017-3-15")+months(1:800))
  all_return_date<-all_return_date[order(all_return_date)]
  
  
  #basic informations
  data1=dbGetQuery(con,'select user_id,order_id,merchant_id,payment_amount,order_date,current_expected_payment_eligibility_date,
                   is_refunded,refund_date,refund_time_diff,loan_operation,has_been_disbursed,is_store_currently_trusted,is_chargeback,
                   order_month,amount,loan_period from t_wish_order')
  data2=dbGetQuery(con,'select merchant_id,admittance_operation,continuous_operation,avg_sales,avg_refund_time_rate,avg_refund_amount_rate from t_merchant_basic_info')
  
  #loaned & returned data
  data4=dbGetQuery(con,'select wish_user_id,num,principal,principal_real,is_yu_qi,yu_qi,end_date from t_repay_plan where is_valid="1"')
  data4$principal<-as.numeric(data4$principal)
  data4$principal_real<-as.numeric(data4$principal_real)
  data4$is_yu_qi<-as.numeric(data4$is_yu_qi)
  data4$yu_qi<-as.numeric(data4$yu_qi)
  data4$end_date<-as.Date(data4$end_date)
  data4[is.na(data4)]<-0
  
  
  n<-c()
  n[1]=length(data1$user_id)
  n[2]=length(data2$merchant_id)
  
  if (n[1] >0 & n[2] > 0){
    data1$merchant_id<-as.character(data1$merchant_id)
    data2$merchant_id<-as.character(data2$merchant_id)
    alldata<-merge(data1,data2,by="merchant_id",all.x=TRUE)
    alldata$order_id<-as.character(alldata$order_id)
    alldata$user_id<-as.character(alldata$user_id)
    alldata$merchant_id<-as.character(alldata$merchant_id)
    alldata$payment_amount<-as.numeric(alldata$payment_amount)
    alldata$order_date<-as.Date(alldata$order_date)
    alldata$current_expected_payment_eligibility_date<-as.Date(alldata$current_expected_payment_eligibility_date)
    alldata$is_refunded<-as.numeric(alldata$is_refunded)
    alldata$refund_time_diff<-as.numeric(alldata$refund_time_diff)
    alldata$loan_operation<-as.numeric(alldata$loan_operation)
    alldata$has_been_disbursed<-as.numeric(alldata$has_been_disbursed)
    alldata$is_store_currently_trusted<-as.numeric(alldata$is_store_currently_trusted)
    alldata$is_chargeback<-as.numeric(alldata$is_chargeback)
    alldata$amount<-as.numeric(alldata$amount)
    alldata$order_month<-format(as.Date(alldata$order_date),"%Y/%m")
    alldata$loan_period<-alldata$current_expected_payment_eligibility_date-alldata$order_date
    alldata$loan_period<-as.numeric(alldata$loan_period)
    alldata$admittance_operation<-as.numeric(alldata$admittance_operation)
    alldata$continuous_operation<-as.numeric(alldata$continuous_operation)
    alldata$avg_sales<-as.numeric(alldata$avg_sales)
    alldata$avg_refund_time_rate<-as.numeric(alldata$avg_refund_time_rate)
    alldata$avg_refund_amount_rate<-as.numeric(alldata$avg_refund_amount_rate)
    alldata[,"order_year"]<-format(as.Date(alldata$order_date),"%Y")
    alldata[is.na(alldata)]<-0
    alldata<-alldata[!duplicated(alldata$order_id),]
    
    
    
    
    #apply users' summary
    num_in<-aggregate(amount~user_id,data=alldata,length)
    num_in<-num_in[!duplicated(num_in$user_id),]
    required_user_id<-num_in$user_id
    
    
    
    
    n1<-length(required_user_id)
    if (n1 > 0){
      result<-list(0)
      credibility<-c()
      refundSituation<-c()
      operationTime<-c()
      stability<-c()
      chargeSituation<-c()
      overdueSituation<-c()
      withdrawalsSituation<-c()
      paymentSituation<-c()
      loaningMoney1<-c()
      loaningMoney2<-c()
      loaningRate1<-c()
      feeSum1<-c()
      hasBeenPassed<-c()
      isObservationNeeded<-c()
      partLoaningRate1<-c()
      partLoaningRate2<-c()
      
      
      
      #30 days product
      for (i in 1:length(required_user_id)){
        
        #next 2 return days
        k<-1
        repeat{
          k<-k+1
          if (today>=all_return_date[k-1] & today<all_return_date[k]) break}
        return_date<-as.data.frame(c(today,all_return_date[c(k,k+1,k+2)]))
        colnames(return_date)<-"expected_return_date"
        k<-1
        repeat{
          k<-k+1
          if (return_date$expected_return_date[1]>=all_return_date[k-1] & return_date$expected_return_date[1]<all_return_date[k]) break}
        return_date$expected_return_date[1]<-all_return_date[k-1]
        return_date[,"starting_date"]<-return_date$expected_return_date-months(3)
        
        
        
        data<-alldata[which(alldata$user_id %in% required_user_id[i]),]
        data<-data[!duplicated(data$order_id),]
        data$order_date<-as.Date(data$order_date)
        
        n2<-length(data$user_id)
        if (n2 <= 0){
          
          credibility[i]<-0
          refundSituation[i]<-0
          operationTime[i]<-0
          stability[i]<-0
          chargeSituation[i]<-0
          overdueSituation[i]<-0
          withdrawalsSituation[i]<-0
          paymentSituation[i]<-0
          loaningMoney1[i]<-0
          loaningMoney2[i]<-0
          loaningRate1[i]<-0
          feeSum1[i]<-0
          hasBeenPassed[i]<-0
          partLoaningRate1[i]<-0
          partLoaningRate2[i]<-0
          
        }else if (n2 > 0){
          
          result[[i]]<-c(0)
          
          
          #Pass Standard#
          #credibility
          newest_date<-max(data$order_date)
          is_store_currently_trusted<-data$is_store_currently_trusted[which(data$order_date %in% newest_date)]
          is_store_currently_trusted[is.na(is_store_currently_trusted)]<-0
          if (0 %in% is_store_currently_trusted) {
            result[[i]][1]=0;credibility[i]=0
          } else {result[[i]][1]=1;credibility[i]=1}
          
          #Refund rate
          data$avg_refund_amount_rate[is.na(data$avg_refund_amount_rate)]<-0
          data$avg_refund_time_rate[is.na(data$avg_refund_time_rate)]<-0
          if (data$avg_refund_amount_rate[1] > 0.15 || data$avg_refund_time_rate[1] > 0.15) {
            result[[i]][2]=0;refundSituation[i]=0
          } else {result[[i]][2]=1;refundSituation[i]=1}
          
          #Operating time
          admittance_operation<-max(data$admittance_operation)
          admittance_operation[is.na(admittance_operation)]<-0
          continuous_operation<-max(data$continuous_operation)
          continuous_operation[is.na(continuous_operation)]<-0
          if (admittance_operation < 12 || continuous_operation < 6) {
            result[[i]][3]=0;operationTime[i]=0
          } else {result[[i]][3]=1;operationTime[i]=1}
          
          #stability
          dfs<-melt(data,measure.vars="amount",id.vars=c("order_month","order_year","user_id"))
          summary<-cast(dfs,order_month+order_year~.,sum,na.rm=T)
          colnames(summary)<-c("order_month","order_year","sales_month")
          summary_year<-aggregate(amount~order_year,data=data,sum)
          colnames(summary_year)<-c("order_year","sales_year")
          summary<-merge(summary,summary_year,by="order_year",all.x=T)
          summary$sales_month<-as.numeric(summary$sales_month)
          summary$sales_year<-as.numeric(summary$sales_year)
          n7<-length(summary$sales_month)
          if (n7 > 0) {
            summary[,'rate']<-summary$sales_month/summary$sales_year
            summary$rate[is.na(summary$rate)]<-0
            summary$rate<-as.numeric(summary$rate)
            today_month<-format(today,"%Y/%m")
            earlest_month<-min(summary$order_month)
            summary<-summary[which(summary$order_month != today_month & summary$order_month != earlest_month),]
            n8<-length(summary$rate)
            if (n8 > 0){
              sta_length<-length(summary$rate[which(summary$rate >= 0.05)])
              summary_length<-length(summary$rate)
              sta_length[is.na(sta_length)]=0
              summary_length[is.na(summary_length)]=0
              if (sta_length < summary_length || sta_length == 0 || summary_length == 0){
                result[[i]][4]=0;stability[i]=0
              } else {result[[i]][4]=1;stability[i]=1}
            }else {result[[i]][4]=1;stability[i]=1}
            
          }else {result[[i]][4]=1;stability[i]=1}
          
          #charge times
          data$is_chargeback[is.na(data$is_chargeback)]<-0
          if (1 %in% data$is_chargeback){
            result[[i]][5]=0;chargeSituation[i]=0
          }else {result[[i]][5]=1;chargeSituation[i]=1}
          
          #overdue
          borrowed_data<-data4[which(data4$wish_user_id %in% required_user_id[i]),]
          n=length(borrowed_data$is_yu_qi)
          n[is.na(n)]<-0
          if (n == 0){
            overdued_or_not<-0
          } else if (n > 0){
            last_borrowed_date<-max(borrowed_data$end_date)
            overdued_or_not<-sum(borrowed_data$is_yu_qi[which(borrowed_data$end_date %in% last_borrowed_date)])
          }
          if (overdued_or_not > 0){
            result[[i]][6]=0;overdueSituation[i]=0
          }else {result[[i]][6]=1;overdueSituation[i]=1}
          
          #sales situation
          data$avg_sales[is.na(data$avg_sales)]<-0
          if (data$avg_sales[1] < 1000){
            result[[i]][8]=0;paymentSituation[i]=0
          } else {result[[i]][8]=1;paymentSituation[i]=1}
          
          
          #return_rate#
          return_rate1<-1
          return_rate2<-length(data$order_date[which(data$loan_period >= 90)])/length(data$order_date[which(data$loan_period >= 60)])
          
          #refund_rate#
          refund_rate1<-length(data$order_date[which(data$refund_time_diff>= 75 & data$loan_period >= 75 
                                                         & data$is_refunded == 1)])/length(data$order_date[which(data$loan_period >= 75)])
          refund_rate2<-length(data$order_date[which(data$refund_time_diff>= 60 & data$loan_period >= 60
                                                         & data$is_refunded == 1)])/length(data$order_date[which(data$loan_period >= 60)])
          return_rate1[is.na(return_rate1)]<-0
          return_rate2[is.na(return_rate2)]<-0
          refund_rate1[is.na(refund_rate1)]<-0
          refund_rate2[is.na(refund_rate2)]<-0
          
          cid<-which(data$order_date >= return_date$starting_date[1] & 
                       data$order_date < return_date$starting_date[3] & data$is_refunded == 0 &
                       data$has_been_disbursed == 0 & data$loan_operation == 0)
          
          n3=length(cid)
          n3[is.na(n3)]<-0
          
          if (n3 <= 0){
            
            loaningMoney<-0
            loaningRate<-0
            rate1<-0
            rate2<-0
            feeSum<-0
            
          }else if (n3 > 0){
            
            loan_data<-data[cid,]
            loan_data<-loan_data[!duplicated(loan_data$order_id),]
            
            #rate
            rate1<-(1-refund_rate1)*return_rate1
            rate2<-(1-refund_rate2)*return_rate2
            rate1[is.na(rate1)]<-0
            rate2[is.na(rate2)]<-0
            if (rate1 >= 0.9){
              rate1=0.9
            }
            
            #money
            sumPayment<-sum(loan_data$amount)
            sumPayment[is.na(sumPayment)]<-0
            loan_money_part1<-sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[1] 
                                                         & loan_data$order_date < return_date$starting_date[2])]) * rate1
            loan_money_part2<-sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[2] 
                                                         & loan_data$order_date < return_date$starting_date[3])]) * rate2
            loan_money_part1[is.na(loan_money_part1)]<-0
            loan_money_part2[is.na(loan_money_part2)]<-0
            loan_money_part1<-as.numeric(loan_money_part1)
            loan_money_part2<-as.numeric(loan_money_part2)
            if (loan_money_part2 > loan_money_part1){
              loan_money_part2=loan_money_part1
            } else if (loan_money_part2 <= loan_money_part1){
              loan_money_part2=loan_money_part2
            }
            loaningMoneyOriginal<-loan_money_part1+loan_money_part2
            loaningRateOriginal<-loaningMoneyOriginal/sumPayment
            loaningRate<-loaningRateOriginal
            loaningMoney<-loaningMoneyOriginal
            loaningRateOriginal[is.na(loaningRateOriginal)]<-0
            if (loaningRateOriginal > 0.8){
              loaningRate<-0.8
              loaningMoney<-sumPayment*0.8
            }
            #owed_money<-sum(borrowed_data$principal)-sum(borrowed_data$principal_real)
            #loaningMoney<-loaningMoneyOriginal-owed_money
            loaningMoney[is.na(loaningMoney)]<-0
            loaningRate<-loaningMoney/sumPayment
            rate<-loaningMoney/loaningMoneyOriginal
            rate1<-loan_money_part1/sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[1] 
                                                               & loan_data$order_date < return_date$starting_date[2])])*rate
            rate2<-loan_money_part2/sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[2] 
                                                               & loan_data$order_date < return_date$starting_date[3])])*rate
            feeSum<-loaningMoney*0.01
            
            
          }
          
          loaningMoney<-as.numeric(loaningMoney)
          loaningMoney[is.na(loaningMoney)]<-0
          loaningRate<-as.numeric(loaningRate)
          loaningRate[is.na(loaningRate)]<-0
          feeSum<-as.numeric(feeSum)
          feeSum[is.na(feeSum)]<-0
          rate1<-as.numeric(rate1)
          rate1[is.na(rate1)]<-0
          rate2<-as.numeric(rate2)
          rate2[is.na(rate2)]<-0
          
          
          #loaning situation
          loaningMoney[is.na(loaningMoney)]<-0
          if (loaningMoney < 1000){
            result[[i]][7]=0;withdrawalsSituation[i]=0
          } else {result[[i]][7]=1;withdrawalsSituation[i]=1}
          
          
          n=length(result[[i]][])
          result[is.na(result)]<-0
          n[is.na(n)]=0
          if (sum(result[[i]][],na.rm=T) == n){
            loaningMoney1[i]<-round(loaningMoney,4)
            loaningMoney2[i]<-round(loaningMoney,4)
            loaningRate1[i]<-round(loaningRate,4)
            feeSum1[i]<-feeSum
            hasBeenPassed[i]<-1
            partLoaningRate1[i]<-round(rate1,4)
            partLoaningRate2[i]<-round(rate2,4)
          } else{
            loaningMoney1[i]<-0
            loaningMoney2[i]<-round(loaningMoney,4)
            loaningRate1[i]<-round(loaningRate,4)
            feeSum1[i]<-feeSum
            hasBeenPassed[i]<-0
            partLoaningRate1[i]<-0
            partLoaningRate2[i]<-0
          }
        }
      }
      
      
      status<-as.numeric(status)
      status[is.na(status)]<-1
      if (status == 1){
        isObservationNeeded<-rep(1,length(required_user_id))
      } else {isObservationNeeded<-rep(0,length(required_user_id))}
      
      
      resultTable1<-data.frame(required_user_id,hasBeenPassed,loaningMoney1,loaningMoney2,loaningRate1,feeSum1,isObservationNeeded,
                              credibility,refundSituation,operationTime,stability,chargeSituation,overdueSituation,
                              withdrawalsSituation,paymentSituation,partLoaningRate1,partLoaningRate2)
      colnames(resultTable1)<-c("user_id","has_been_passed","quota","quota_original","quota_rate","interest","is_observation_needed",
                               "credibility","refund_situation","operation_time","stability","charge_situation","overdue_situation",
                               "withdrawals_situation","payment_situation","part_loaning_rate1","part_loaning_rate2")
      
      resultTable1[,'update_time']<-Sys.time()
      resultTable1[,'product_type']<-1
      resultTable1[is.na(resultTable1)]<-0
      dbGetQuery(con,"set names utf8")
      dbWriteTable(con,"result_records",resultTable1,row.names=F,append=T)
      
      
      #60 days product
      for (i in 1:length(required_user_id)){
        
        #next 2 return days
        k<-1
        repeat{
          k<-k+1
          if (today>=all_return_date[k-1] & today<all_return_date[k]) break}
        return_date<-as.data.frame(all_return_date[c(k+1,k+2,k+3,k+4)])
        colnames(return_date)<-"expected_return_date"
        return_date[,"starting_date"]<-return_date$expected_return_date-months(3)
        
        
        
        data<-alldata[which(alldata$user_id %in% required_user_id[i]),]
        data<-data[!duplicated(data$order_id),]
        data$order_date<-as.Date(data$order_date)
        
        n2<-length(data$user_id)
        if (n2 <= 0){
          
          credibility[i]<-0
          refundSituation[i]<-0
          operationTime[i]<-0
          stability[i]<-0
          chargeSituation[i]<-0
          overdueSituation[i]<-0
          withdrawalsSituation[i]<-0
          paymentSituation[i]<-0
          loaningMoney1[i]<-0
          loaningMoney2[i]<-0
          loaningRate1[i]<-0
          feeSum1[i]<-0
          hasBeenPassed[i]<-0
          partLoaningRate1[i]<-0
          partLoaningRate2[i]<-0
          
        }else if (n2 > 0){
          
          result[[i]]<-c(0)
          
          
          #Pass Standard#
          #credibility
          newest_date<-max(data$order_date)
          is_store_currently_trusted<-data$is_store_currently_trusted[which(data$order_date %in% newest_date)]
          is_store_currently_trusted[is.na(is_store_currently_trusted)]<-0
          if (0 %in% is_store_currently_trusted) {
            result[[i]][1]=0;credibility[i]=0
          } else {result[[i]][1]=1;credibility[i]=1}
          
          #Refund rate
          data$avg_refund_amount_rate[is.na(data$avg_refund_amount_rate)]<-0
          data$avg_refund_time_rate[is.na(data$avg_refund_time_rate)]<-0
          if (data$avg_refund_amount_rate[1] > 0.15 || data$avg_refund_time_rate[1] > 0.15) {
            result[[i]][2]=0;refundSituation[i]=0
          } else {result[[i]][2]=1;refundSituation[i]=1}
          
          #Operating time
          admittance_operation<-max(data$admittance_operation)
          admittance_operation[is.na(admittance_operation)]<-0
          continuous_operation<-max(data$continuous_operation)
          continuous_operation[is.na(continuous_operation)]<-0
          if (admittance_operation < 24 || continuous_operation < 12) {
            result[[i]][3]=0;operationTime[i]=0
          } else {result[[i]][3]=1;operationTime[i]=1}
          
          #stability
          dfs<-melt(data,measure.vars="amount",id.vars=c("order_month","order_year","user_id"))
          summary<-cast(dfs,order_month+order_year~.,sum,na.rm=T)
          colnames(summary)<-c("order_month","order_year","sales_month")
          summary_year<-aggregate(amount~order_year,data=data,sum)
          colnames(summary_year)<-c("order_year","sales_year")
          summary<-merge(summary,summary_year,by="order_year",all.x=T)
          summary$sales_month<-as.numeric(summary$sales_month)
          summary$sales_year<-as.numeric(summary$sales_year)
          n7<-length(summary$sales_month)
          if (n7 > 0) {
            summary[,'rate']<-summary$sales_month/summary$sales_year
            summary$rate[is.na(summary$rate)]<-0
            summary$rate<-as.numeric(summary$rate)
            today_month<-format(today,"%Y/%m")
            earlest_month<-min(summary$order_month)
            summary<-summary[which(summary$order_month != today_month & summary$order_month != earlest_month),]
            n8<-length(summary$rate)
            if (n8 > 0){
              sta_length<-length(summary$rate[which(summary$rate >= 0.05)])
              summary_length<-length(summary$rate)
              sta_length[is.na(sta_length)]=0
              summary_length[is.na(summary_length)]=0
              if (sta_length < summary_length || sta_length == 0 || summary_length == 0){
                result[[i]][4]=0;stability[i]=0
              } else {result[[i]][4]=1;stability[i]=1}
            }else {result[[i]][4]=1;stability[i]=1}
            
          }else {result[[i]][4]=1;stability[i]=1}
          
          #charge times
          data$is_chargeback[is.na(data$is_chargeback)]<-0
          if (1 %in% data$is_chargeback){
            result[[i]][5]=0;chargeSituation[i]=0
          }else {result[[i]][5]=1;chargeSituation[i]=1}
          
          #overdue
          borrowed_data<-data4[which(data4$wish_user_id %in% required_user_id[i]),]
          n=length(borrowed_data$is_yu_qi)
          n[is.na(n)]<-0
          if (n == 0){
            overdued_or_not<-0
          } else if (n > 0){
            last_borrowed_date<-max(borrowed_data$end_date)
            overdued_or_not<-sum(borrowed_data$is_yu_qi[which(borrowed_data$end_date %in% last_borrowed_date)])
          }
          if (overdued_or_not > 0){
            result[[i]][6]=0;overdueSituation[i]=0
          }else {result[[i]][6]=1;overdueSituation[i]=1}
          
          #sales situation
          data$avg_sales[is.na(data$avg_sales)]<-0
          if (data$avg_sales[1] < 1000){
            result[[i]][8]=0;paymentSituation[i]=0
          } else {result[[i]][8]=1;paymentSituation[i]=1}
          
          
          #return_rate#
          return_rate1<-length(data$order_date[which(data$loan_period >= 90)])/length(data$order_date[which(data$loan_period >= 45)])
          return_rate2<-length(data$order_date[which(data$loan_period >= 90)])/length(data$order_date[which(data$loan_period >= 30)])
          #refund_rate#
          refund_rate1<-length(data$order_date[which(data$refund_time_diff >= 45 & data$loan_period >= 45
                                                         & data$is_refunded == 1)])/length(data$order_date[which(data$loan_period >= 45)])
          refund_rate2<-length(data$order_date[which(data$refund_time_diff >= 30 & data$loan_period >= 30
                                                         & data$is_refunded == 1)])/length(data$order_date[which(data$loan_period >= 30)])
          return_rate1[is.na(return_rate1)]<-0
          return_rate2[is.na(return_rate2)]<-0
          refund_rate1[is.na(refund_rate1)]<-0
          refund_rate2[is.na(refund_rate2)]<-0
          
          cid<-which(data$order_date >= return_date$starting_date[1] & 
                       data$order_date < return_date$starting_date[3] & data$is_refunded == 0 &
                       data$has_been_disbursed == 0 & data$loan_operation == 0)
          
          n3=length(cid)
          n3[is.na(n3)]<-0
          
          if (n3 <= 0){
            
            loaningMoney<-0
            loaningRate<-0
            rate1<-0
            rate2<-0
            feeSum<-0
            
          }else if (n3 > 0){
            
            loan_data<-data[cid,]
            loan_data<-loan_data[!duplicated(loan_data$order_id),]
            
            #rate
            rate1<-(1-refund_rate1)*return_rate1
            rate2<-(1-refund_rate2)*return_rate2
            rate1[is.na(rate1)]<-0
            rate2[is.na(rate2)]<-0
            if (rate1 >= 0.9){
              rate1=0.9
            }
            
            #money
            sumPayment<-sum(loan_data$amount)
            sumPayment[is.na(sumPayment)]<-0
            loan_money_part1<-sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[1] 
                                                         & loan_data$order_date < return_date$starting_date[2])]) * rate1
            loan_money_part2<-sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[2] 
                                                         & loan_data$order_date < return_date$starting_date[3])]) * rate2
            loan_money_part1[is.na(loan_money_part1)]<-0
            loan_money_part2[is.na(loan_money_part2)]<-0
            loan_money_part1<-as.numeric(loan_money_part1)
            loan_money_part2<-as.numeric(loan_money_part2)
            if (loan_money_part2 > loan_money_part1){
              loan_money_part2=loan_money_part1
            } else if (loan_money_part2 <= loan_money_part1){
              loan_money_part2=loan_money_part2
            }
            loaningMoneyOriginal<-loan_money_part1+loan_money_part2
            loaningRateOriginal<-loaningMoneyOriginal/sumPayment
            loaningRate<-loaningRateOriginal
            loaningMoney<-loaningMoneyOriginal
            loaningRateOriginal[is.na(loaningRateOriginal)]<-0
            if (loaningRateOriginal > 0.8){
              loaningRate<-0.8
              loaningMoney<-sumPayment*0.8
            }
            #owed_money<-sum(borrowed_data$principal)-sum(borrowed_data$principal_real)
            #loaningMoney<-loaningMoneyOriginal-owed_money
            loaningMoney[is.na(loaningMoney)]<-0
            loaningRate<-loaningMoney/sumPayment
            rate<-loaningMoney/loaningMoneyOriginal
            rate1<-loan_money_part1/sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[1] 
                                                               & loan_data$order_date < return_date$starting_date[2])])*rate
            rate2<-loan_money_part2/sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[2] 
                                                               & loan_data$order_date < return_date$starting_date[3])])*rate
            feeSum<-loaningMoney*0.01
            
            
          }
          
          loaningMoney<-as.numeric(loaningMoney)
          loaningMoney[is.na(loaningMoney)]<-0
          loaningRate<-as.numeric(loaningRate)
          loaningRate[is.na(loaningRate)]<-0
          feeSum<-as.numeric(feeSum)
          feeSum[is.na(feeSum)]<-0
          rate1<-as.numeric(rate1)
          rate1[is.na(rate1)]<-0
          rate2<-as.numeric(rate2)
          rate2[is.na(rate2)]<-0
          
          
          #loaning situation
          loaningMoney[is.na(loaningMoney)]<-0
          if (loaningMoney < 1000){
            result[[i]][7]=0;withdrawalsSituation[i]=0
          } else {result[[i]][7]=1;withdrawalsSituation[i]=1}
          
          
          n=length(result[[i]][])
          result[is.na(result)]<-0
          n[is.na(n)]=0
          if (sum(result[[i]][],na.rm=T) == n){
            loaningMoney1[i]<-round(loaningMoney,4)
            loaningMoney2[i]<-round(loaningMoney,4)
            loaningRate1[i]<-round(loaningRate,4)
            feeSum1[i]<-feeSum
            hasBeenPassed[i]<-1
            partLoaningRate1[i]<-round(rate1,4)
            partLoaningRate2[i]<-round(rate2,4)
          } else{
            loaningMoney1[i]<-0
            loaningMoney2[i]<-round(loaningMoney,4)
            loaningRate1[i]<-round(loaningRate,4)
            feeSum1[i]<-feeSum
            hasBeenPassed[i]<-0
            partLoaningRate1[i]<-0
            partLoaningRate2[i]<-0
          }
        }
      }
      status<-as.numeric(status)
      status[is.na(status)]<-1
      if (status == 1){
        isObservationNeeded<-rep(1,length(required_user_id))
      } else {isObservationNeeded<-rep(0,length(required_user_id))}
      
      resultTable2<-data.frame(required_user_id,hasBeenPassed,loaningMoney1,loaningMoney2,loaningRate1,feeSum1,isObservationNeeded,
                              credibility,refundSituation,operationTime,stability,chargeSituation,overdueSituation,
                              withdrawalsSituation,paymentSituation,partLoaningRate1,partLoaningRate2)
      colnames(resultTable2)<-c("user_id","has_been_passed","quota","quota_original","quota_rate","interest","is_observation_needed",
                               "credibility","refund_situation","operation_time","stability","charge_situation","overdue_situation",
                               "withdrawals_situation","payment_situation","part_loaning_rate1","part_loaning_rate2")
      
      resultTable2[,'update_time']<-Sys.time()
      resultTable2[,'product_type']<-2
      resultTable2[is.na(resultTable2)]<-0
      dbGetQuery(con,"set names utf8")
      dbWriteTable(con,"result_records",resultTable2,row.names=F,append=T)
      
      
      
      
      #90 days product
      for (i in 1:length(required_user_id)){
        
        #next 2 return days
        k<-1
        repeat{
          k<-k+1
          if (today>=all_return_date[k-1] & today<all_return_date[k]) break}
        return_date<-as.data.frame(all_return_date[c(k+3,k+4,k+5,k+6)])
        colnames(return_date)<-"expected_return_date"
        return_date[,"starting_date"]<-return_date$expected_return_date-months(3)
        
        
        
        data<-alldata[which(alldata$user_id %in% required_user_id[i]),]
        data<-data[!duplicated(data$order_id),]
        data$order_date<-as.Date(data$order_date)
        
        n2<-length(data$user_id)
        if (n2 <= 0){
          
          credibility[i]<-0
          refundSituation[i]<-0
          operationTime[i]<-0
          stability[i]<-0
          chargeSituation[i]<-0
          overdueSituation[i]<-0
          withdrawalsSituation[i]<-0
          paymentSituation[i]<-0
          loaningMoney1[i]<-0
          loaningMoney2[i]<-0
          loaningRate1[i]<-0
          feeSum1[i]<-0
          hasBeenPassed[i]<-0
          partLoaningRate1[i]<-0
          partLoaningRate2[i]<-0
          
        }else if (n2 > 0){
          
          result[[i]]<-c(0)
          
          
          #Pass Standard#
          #credibility
          newest_date<-max(data$order_date)
          is_store_currently_trusted<-data$is_store_currently_trusted[which(data$order_date %in% newest_date)]
          is_store_currently_trusted[is.na(is_store_currently_trusted)]<-0
          if (0 %in% is_store_currently_trusted) {
            result[[i]][1]=0;credibility[i]=0
          } else {result[[i]][1]=1;credibility[i]=1}
          
          #Refund rate
          data$avg_refund_amount_rate[is.na(data$avg_refund_amount_rate)]<-0
          data$avg_refund_time_rate[is.na(data$avg_refund_time_rate)]<-0
          if (data$avg_refund_amount_rate[1] > 0.15 || data$avg_refund_time_rate[1] > 0.15) {
            result[[i]][2]=0;refundSituation[i]=0
          } else {result[[i]][2]=1;refundSituation[i]=1}
          
          #Operating time
          admittance_operation<-max(data$admittance_operation)
          admittance_operation[is.na(admittance_operation)]<-0
          continuous_operation<-max(data$continuous_operation)
          continuous_operation[is.na(continuous_operation)]<-0
          if (admittance_operation < 36 || continuous_operation < 18) {
            result[[i]][3]=0;operationTime[i]=0
          } else {result[[i]][3]=1;operationTime[i]=1}
          
          #stability
          dfs<-melt(data,measure.vars="amount",id.vars=c("order_month","order_year","user_id"))
          summary<-cast(dfs,order_month+order_year~.,sum,na.rm=T)
          colnames(summary)<-c("order_month","order_year","sales_month")
          summary_year<-aggregate(amount~order_year,data=data,sum)
          colnames(summary_year)<-c("order_year","sales_year")
          summary<-merge(summary,summary_year,by="order_year",all.x=T)
          summary$sales_month<-as.numeric(summary$sales_month)
          summary$sales_year<-as.numeric(summary$sales_year)
          n7<-length(summary$sales_month)
          if (n7 > 0) {
            summary[,'rate']<-summary$sales_month/summary$sales_year
            summary$rate[is.na(summary$rate)]<-0
            summary$rate<-as.numeric(summary$rate)
            today_month<-format(today,"%Y/%m")
            earlest_month<-min(summary$order_month)
            summary<-summary[which(summary$order_month != today_month & summary$order_month != earlest_month),]
            n8<-length(summary$rate)
            if (n8 > 0){
              sta_length<-length(summary$rate[which(summary$rate >= 0.05)])
              summary_length<-length(summary$rate)
              sta_length[is.na(sta_length)]=0
              summary_length[is.na(summary_length)]=0
              if (sta_length < summary_length || sta_length == 0 || summary_length == 0){
                result[[i]][4]=0;stability[i]=0
              } else {result[[i]][4]=1;stability[i]=1}
            }else {result[[i]][4]=1;stability[i]=1}
            
          }else {result[[i]][4]=1;stability[i]=1}
          
          #charge times
          data$is_chargeback[is.na(data$is_chargeback)]<-0
          if (1 %in% data$is_chargeback){
            result[[i]][5]=0;chargeSituation[i]=0
          }else {result[[i]][5]=1;chargeSituation[i]=1}
          
          #overdue
          borrowed_data<-data4[which(data4$wish_user_id %in% required_user_id[i]),]
          n=length(borrowed_data$is_yu_qi)
          n[is.na(n)]<-0
          if (n == 0){
            overdued_or_not<-0
          } else if (n > 0){
            last_borrowed_date<-max(borrowed_data$end_date)
            overdued_or_not<-sum(borrowed_data$is_yu_qi[which(borrowed_data$end_date %in% last_borrowed_date)])
          }
          if (overdued_or_not > 0){
            result[[i]][6]=0;overdueSituation[i]=0
          }else {result[[i]][6]=1;overdueSituation[i]=1}
          
          #sales situation
          data$avg_sales[is.na(data$avg_sales)]<-0
          if (data$avg_sales[1] < 1000){
            result[[i]][8]=0;paymentSituation[i]=0
          } else {result[[i]][8]=1;paymentSituation[i]=1}
          
          
          #return_rate#
          return_rate1<-length(data$order_date[which(data$loan_period >= 90)])/length(data$order_date[which(data$loan_period >= 15)])
          return_rate2<-length(data$order_date[which(data$loan_period >= 90)])/length(data$order_date)
          #refund_rate#
          refund_rate1<-length(data$order_date[which(data$refund_time_diff >= 15 & data$is_refunded == 1)])/length(data$order_date[which(data$loan_period >= 15)])
          refund_rate2<-length(data$order_date[which(data$is_refunded == 1)])/length(data$order_date)
          return_rate1[is.na(return_rate1)]<-0
          return_rate2[is.na(return_rate2)]<-0
          refund_rate1[is.na(refund_rate1)]<-0
          refund_rate2[is.na(refund_rate2)]<-0
          
          
          cid<-which(data$order_date >= return_date$starting_date[1] & 
                       data$order_date < return_date$starting_date[3] & data$is_refunded == 0 &
                       data$has_been_disbursed == 0 & data$loan_operation == 0)
          
          n3=length(cid)
          n3[is.na(n3)]<-0
          
          if (n3 <= 0){
            
            loaningMoney<-0
            loaningRate<-0
            rate1<-0
            rate2<-0
            feeSum<-0
            
          }else if (n3 > 0){
            
            loan_data<-data[cid,]
            loan_data<-loan_data[!duplicated(loan_data$order_id),]
            
            #rate
            rate1<-(1-refund_rate1)*return_rate1
            rate2<-(1-refund_rate2)*return_rate2
            rate1[is.na(rate1)]<-0
            rate2[is.na(rate2)]<-0
            if (rate1 >= 0.9){
              rate1=0.9
            }
            
            #money
            sumPayment<-sum(loan_data$amount)
            sumPayment[is.na(sumPayment)]<-0
            loan_money_part1<-sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[1] 
                                                         & loan_data$order_date < return_date$starting_date[2])]) * rate1
            loan_money_part2<-sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[2] 
                                                         & loan_data$order_date < return_date$starting_date[3])]) * rate2
            loan_money_part1[is.na(loan_money_part1)]<-0
            loan_money_part2[is.na(loan_money_part2)]<-0
            loan_money_part1<-as.numeric(loan_money_part1)
            loan_money_part2<-as.numeric(loan_money_part2)
            if (loan_money_part2 > loan_money_part1){
              loan_money_part2=loan_money_part1
            } else if (loan_money_part2 <= loan_money_part1){
              loan_money_part2=loan_money_part2
            }
            loaningMoneyOriginal<-loan_money_part1+loan_money_part2
            loaningRateOriginal<-loaningMoneyOriginal/sumPayment
            loaningRate<-loaningRateOriginal
            loaningMoney<-loaningMoneyOriginal
            loaningRateOriginal[is.na(loaningRateOriginal)]<-0
            if (loaningRateOriginal > 0.8){
              loaningRate<-0.8
              loaningMoney<-sumPayment*0.8
            }
            #owed_money<-sum(borrowed_data$principal)-sum(borrowed_data$principal_real)
            #loaningMoney<-loaningMoneyOriginal-owed_money
            loaningMoney[is.na(loaningMoney)]<-0
            loaningRate<-loaningMoney/sumPayment
            rate<-loaningMoney/loaningMoneyOriginal
            rate1<-loan_money_part1/sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[1] 
                                                               & loan_data$order_date < return_date$starting_date[2])])*rate
            rate2<-loan_money_part2/sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[2] 
                                                               & loan_data$order_date < return_date$starting_date[3])])*rate
            feeSum<-loaningMoney*0.01
            
            
          }
          
          loaningMoney<-as.numeric(loaningMoney)
          loaningMoney[is.na(loaningMoney)]<-0
          loaningRate<-as.numeric(loaningRate)
          loaningRate[is.na(loaningRate)]<-0
          feeSum<-as.numeric(feeSum)
          feeSum[is.na(feeSum)]<-0
          rate1<-as.numeric(rate1)
          rate1[is.na(rate1)]<-0
          rate2<-as.numeric(rate2)
          rate2[is.na(rate2)]<-0
          
          
          #loaning situation
          loaningMoney[is.na(loaningMoney)]<-0
          if (loaningMoney < 1000){
            result[[i]][7]=0;withdrawalsSituation[i]=0
          } else {result[[i]][7]=1;withdrawalsSituation[i]=1}
          
          
          n=length(result[[i]][])
          result[is.na(result)]<-0
          n[is.na(n)]=0
          if (sum(result[[i]][],na.rm=T) == n){
            loaningMoney1[i]<-round(loaningMoney,4)
            loaningMoney2[i]<-round(loaningMoney,4)
            loaningRate1[i]<-round(loaningRate,4)
            feeSum1[i]<-feeSum
            hasBeenPassed[i]<-1
            partLoaningRate1[i]<-round(rate1,4)
            partLoaningRate2[i]<-round(rate2,4)
          } else{
            loaningMoney1[i]<-0
            loaningMoney2[i]<-round(loaningMoney,4)
            loaningRate1[i]<-round(loaningRate,4)
            feeSum1[i]<-feeSum
            hasBeenPassed[i]<-0
            partLoaningRate1[i]<-0
            partLoaningRate2[i]<-0
          }
        }
      }
      
      
      status<-as.numeric(status)
      status[is.na(status)]<-1
      if (status == 1){
        isObservationNeeded<-rep(1,length(required_user_id))
      } else {isObservationNeeded<-rep(0,length(required_user_id))}
      
      
      resultTable3<-data.frame(required_user_id,hasBeenPassed,loaningMoney1,loaningMoney2,loaningRate1,feeSum1,isObservationNeeded,
                              credibility,refundSituation,operationTime,stability,chargeSituation,overdueSituation,
                              withdrawalsSituation,paymentSituation,partLoaningRate1,partLoaningRate2)
      colnames(resultTable3)<-c("user_id","has_been_passed","quota","quota_original","quota_rate","interest","is_observation_needed",
                               "credibility","refund_situation","operation_time","stability","charge_situation","overdue_situation",
                               "withdrawals_situation","payment_situation","part_loaning_rate1","part_loaning_rate2")
      
      resultTable3[,'update_time']<-Sys.time()
      resultTable3[,'product_type']<-3
      resultTable3[is.na(resultTable3)]<-0
      dbGetQuery(con,"set names utf8")
      dbWriteTable(con,"result_records",resultTable3,row.names=F,append=T)
      
      
      
      
    }
 
    
    }
  dbDisconnect(con)

}

