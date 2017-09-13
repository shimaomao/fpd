operation=function(loanContractId,applicationMoney){
  loanContractId<-as.character(loanContractId)
  applicationMoney<-as.numeric(applicationMoney)
  applicationMoney[is.na(applicationMoney)]<-0
  
  
  #cache space
  memory.limit(10240000)
  today<-Sys.Date()
  
  
  #library#
  library(DBI)
  library(RMySQL)
  library(lubridate)
  library(reshape)
  
  
  #time series dealing#
  all_return_date<-c(as.Date("2017-3-1")+months(1:800),as.Date("2017-3-15")+months(1:800))
  all_return_date<-all_return_date[order(all_return_date)]
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
  
  
  
  
  #get data#
  #connet MySQL
  con = dbConnect(
    dbDriver("MySQL"),
    #user    = username,
    #password= password,
    #dbname  = dbname,
    #host    = host
    user    = "root",
    password= "123456",
    dbname  = "wf_wish",
    #host    = "192.168.1.2"
    host    = "cskk.f3322.net",
    port = 85
  )
  
  
  #basic informations
  data1=dbGetQuery(con,'select user_id,order_id,merchant_id,payment_amount,order_date,current_expected_payment_eligibility_date,
                   is_refunded,refund_date,loan_operation,has_been_disbursed,order_month,amount,loan_period from t_wish_order')
  data2=dbGetQuery(con,'select id,customer_id from t_loan_contract')
  
  resultTable=dbGetQuery(con,'select user_id,quota,part_loaning_rate1,part_loaning_rate2,update_time from result_records')
  
  data2$id<-as.character(data2$id)
  user_id<-data2$customer_id[which(data2$id %in% loanContractId)]
  
  n<-c()
  n[1]=length(data1$user_id)
  n[2]=length(user_id)
  n[is.na(n)]<-0
  if (n[1] <= 0 || n[2] <= 0){
    result<-paste(0,0,0,0,0,0,0,0,0,sep = ",",collapse=",")
    return(result)
    dbDisconnect(con)
    #user_id<-0                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    #loanMoneyPart1<-0
    #loanMoneyPart2<-0
    #successful_or_not<-0
    
  }else if(n[1] > 0 & n[2] > 0){
    alldata<-data1
    alldata$user_id<-as.character(alldata$user_id)
    alldata$order_id<-as.character(alldata$order_id)
    alldata$merchant_id<-as.character(alldata$merchant_id)
    alldata$payment_amount<-as.numeric(alldata$payment_amount)
    alldata$order_date<-as.Date(alldata$order_date)
    alldata$current_expected_payment_eligibility_date<-as.Date(alldata$current_expected_payment_eligibility_date)
    alldata$is_refunded<-as.numeric(alldata$is_refunded)
    alldata$loan_operation<-as.numeric(alldata$loan_operation)
    alldata$has_been_disbursed<-as.numeric(alldata$has_been_disbursed)
    alldata$order_month<-format(as.Date(alldata$order_date),"%Y/%m")
    alldata$loan_period<-alldata$current_expected_payment_eligibility_date-alldata$order_date
    alldata$loan_period<-as.numeric(alldata$loan_period)
    alldata[is.na(alldata)]<-0
    alldata<-alldata[!duplicated(alldata$order_id),]
    
    #user information
    data<-alldata[which(alldata$user_id %in% user_id),]
    n2=length(data$user_id)
    if (n2 <= 0){
      dbDisconnect(con)
      result<-paste(0,0,0,0,0,0,0,0,0,sep = ",",collapse=",")
      return(result)
      #loanMoneyPart1<-0
      #loanMoneyPart2<-0
      #successful_or_not<-0
      
    }else if (n2 > 0){
      #calculated quota#
      newest_time<-max(resultTable$update_time)
      quota<-resultTable[which(resultTable$user_id %in% user_id & resultTable$update_time == newest_time),]
      quota[is.na(quota)]<-0
      n3<-length(quota$quota)
      
      
      #loan_data
      cid<-which(data$order_date >= return_date$starting_date[1] & 
                   data$order_date < return_date$starting_date[3] & data$is_refunded == 0 &
                   data$has_been_disbursed == 0 & data$loan_operation == 0)
      loan_data<-data[cid,]
      n4<-length(cid)
      
      n3[is.na(n3)]<-0
      n4[is.na(n4)]<-0
      
      
      if(n3 <= 0 || n4 <= 0 || applicationMoney < 1000 || quota$quota==0 || applicationMoney > quota$quota){
        dbDisconnect(con)
        loanMoneyPart1<-0
        loanMoneyPart2<-0
        successful_or_not<-0
        
        
      }else if (n3 > 0 & n4 > 0 & applicationMoney >= 1000 & quota$quota > 0 & applicationMoney <= quota$quota){
        
        rate1<-quota$part_loaning_rate1
        rate1[is.na(rate1)]<-0
        rate2<-quota$part_loaning_rate2
        rate2[is.na(rate2)]<-0
        
        
        if (applicationMoney == quota$quota & quota$quota>0){
          
          loanMoneyPart1<-sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[1] 
                                                     & loan_data$order_date < return_date$starting_date[2])]) * rate1
          #loanMoneyPart2<-sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[2] 
          #                                           & loan_data$order_date < return_date$starting_date[3])]) * rate2
          loanMoneyPart2<-applicationMoney-loanMoneyPart1
          successful_or_not<-1
          
          
          loan_data1<-loan_data
          n5<-length(loan_data1$loan_operation)
          if (n5 > 0){
            loan_data1$loan_operation[which(loan_data1$loan_operation==0)]<-1
            loan_data1[,'update_time']<-Sys.time()
            m<-length(loan_data1$order_id)
            loan_contract_id<-rep(loanContractId,m)
            loan_contract_id[is.na(loan_contract_id)]<-0
            loan_data1[is.na(loan_data1)]<-0
            loan_data2<-data.frame(loan_data1$order_id,loan_contract_id,loan_data1$user_id,loan_data1$payment_amount,loan_data1$amount,loan_data1$order_date,
                                   loan_data1$loan_operation,loan_data1$update_time)
            colnames(loan_data2)<-c('order_id','loan_contract_id','user_id','payment_amount','amount','order_date','loan_operation','update_time')
            loan_data2<-loan_data2[!duplicated(loan_data2$order_id),]
            
            dbGetQuery(con,"set names utf8")
            dbWriteTable(con,"loan_records",loan_data2,row.names=FALSE,overwrite=T)
            sql1=paste("UPDATE t_wish_order o,loan_records r SET o.loan_operation=r.loan_operation WHERE o.order_id=r.order_id;", sep="");
            ips1<-dbGetQuery(con, sql1)
            sql2=paste("UPDATE t_wish_order o,loan_records r SET o.loan_contract_id=r.loan_contract_id WHERE o.order_id=r.order_id;", sep="");
            ips2<-dbGetQuery(con, sql2)
            dbDisconnect(con)
            successful_or_not<-1
            
            
          }else if (n5 <= 0){
            dbDisconnect(con)
            successful_or_not<-0
          }
          
          
          
        }  else if (applicationMoney < quota$quota & quota$quota > 0){
          
          loan_data[,'amount1']<-0
          
          for (i in 1:length(loan_data$amount1)){
            if (loan_data$order_date[i] >= return_date$starting_date[1] & loan_data$order_date[i] < return_date$starting_date[2]){
              loan_data$amount1[i]<-loan_data$amount[i]*rate1
            } else if (loan_data$order_date[i] >= return_date$starting_date[2] & loan_data$order_date[i] < return_date$starting_date[3]){
              loan_data$amount1[i]<-loan_data$amount[i]*rate2
            }
          }
          loan_data$amount1[is.na(loan_data$amount1)]<-0
          loan_data$amount1<-as.numeric(loan_data$amount1)
          loan_data$amount1[is.na(loan_data$amount1)]<-0
          
          sumPayment<-sum(loan_data$amount1[which(loan_data$order_date >= return_date$starting_date[1] & loan_data$order_date < return_date$starting_date[3])])
          sumPayment<-as.numeric(sumPayment)
          sumPayment[is.na(sumPayment)]<-0
          
          if (sumPayment < 1000){
            dbDisconnect(con)
            loanMoneyPart1<-0
            loanMoneyPart2<-0
            successful_or_not<-0
            
          } else if (sumPayment >= 1000){
            
            if (sumPayment < applicationMoney){
              
              loanMoneyPart1<-sum(loan_data$amount1[which(loan_data$order_date >= return_date$starting_date[1] & loan_data$order_date < return_date$starting_date[2])])
              loanMoneyPart2<-sum(loan_data$amount1[which(loan_data$order_date >= return_date$starting_date[2] & loan_data$order_date < return_date$starting_date[3])])
              
              
              loan_data1<-loan_data
              n6<-length(loan_data1$loan_operation)
              if (n6 > 0){
                m<-length(loan_data1$order_id)
                loan_contract_id<-rep(loanContractId,m)
                loan_contract_id[is.na(loan_contract_id)]<-0
                loan_data1[is.na(loan_data1)]<-0
                loan_data1$loan_operation<-1
                loan_data1[,'update_time']<-Sys.time()
                loan_data2<-data.frame(loan_data1$order_id,loan_contract_id,loan_data1$user_id,loan_data1$payment_amount,loan_data1$amount,loan_data1$order_date,
                                       loan_data1$loan_operation,loan_data1$update_time)
                colnames(loan_data2)<-c('order_id','loan_contract_id','user_id','payment_amount','amount','order_date','loan_operation','update_time')
                loan_data2<-loan_data2[!duplicated(loan_data2$order_id),]
                
                dbGetQuery(con,"set names utf8")
                dbWriteTable(con,"loan_records",loan_data2,row.names=FALSE,overwrite=T)
                sql1=paste("UPDATE t_wish_order o,loan_records r SET o.loan_operation=r.loan_operation WHERE o.order_id=r.order_id;", sep="");
                ips1<-dbGetQuery(con, sql1)
                sql2=paste("UPDATE t_wish_order o,loan_records r SET o.loan_contract_id=r.loan_contract_id WHERE o.order_id=r.order_id;", sep="");
                ips2<-dbGetQuery(con, sql2)
                dbDisconnect(con)
                successful_or_not<-1
                
                
              } else if (n6 <= 0){
                dbDisconnect(con)
                successful_or_not<-0
              }
              
              
            } else if (sumPayment >= applicationMoney){
              
              sum<-c()
              sum[1]<-0
              i<-1
              repeat{
                sum[i+1]<-loan_data$amount1[i]+sum[i]  #back to front
                i<-i+1
                sum[is.na(sum)]<-0
                if (sum[i]>=applicationMoney) break}
              loanMaxDate<-loan_data$order_date[i-1]
              loanMoneyPart1<-sum(loan_data$amount[which(loan_data$order_date >= return_date$starting_date[1] 
                                                         & loan_data$order_date < return_date$starting_date[2])]) * rate1
              loan_data1<-loan_data[c(1:(i-1)),]
              m<-length(loan_data1$order_id)
              loan_contract_id<-rep(loanContractId,m)
              n7<-length(loan_data1$loan_operation[which(loan_data1$loan_operation==0 & loan_data1$order_date <= loanMaxDate)])
              if (n7 > 0){
                loan_data1$loan_operation[which(loan_data1$loan_operation==0 & loan_data1$order_date <= loanMaxDate)]<-1
                loan_data1[,'update_time']<-Sys.time()
                loan_data2<-data.frame(loan_data1$order_id,loan_contract_id,loan_data1$user_id,loan_data1$payment_amount,loan_data1$amount,loan_data1$order_date,
                                       loan_data1$loan_operation,loan_data1$update_time)
                colnames(loan_data2)<-c('order_id','loan_contract_id','user_id','payment_amount','amount','order_date','loan_operation','update_time')
                loan_data2<-loan_data2[!duplicated(loan_data2$order_id),]
                
                dbGetQuery(con,"set names utf8")
                dbWriteTable(con,"loan_records",loan_data2,row.names=FALSE,overwrite=T)
                sql1=paste("UPDATE t_wish_order o,loan_records r SET o.loan_operation=r.loan_operation WHERE o.order_id=r.order_id;", sep="");
                ips1<-dbGetQuery(con, sql1)
                sql2=paste("UPDATE t_wish_order o,loan_records r SET o.loan_contract_id=r.loan_contract_id WHERE o.order_id=r.order_id;", sep="");
                ips2<-dbGetQuery(con, sql2)
                dbDisconnect(con)
                
                loanMoneyPart1[is.na(loanMoneyPart1)]<-0
                if (applicationMoney <= loanMoneyPart1){
                  loanMoneyPart1<-applicationMoney
                  loanMoneyPart2<-0
                } else if (applicationMoney > loanMoneyPart1){
                  loanMoneyPart2<-applicationMoney-loanMoneyPart1
                }
                successful_or_not<-1
                
                
              } else if(n7 <= 0){
                successful_or_not<-0
                dbDisconnect(con)
              }
              
            }
          }
          
          
          
          
        }
      }
      
      #return result#
      user_id[is.na(user_id)]<-0
      user_id<-as.character(user_id)
      #application money
      application_money<-as.numeric(applicationMoney)
      application_money<-as.character(application_money)
      #return_money1
      loanMoneyPart1<-as.numeric(loanMoneyPart1)
      return_money1<-round(loanMoneyPart1,4)
      return_money1<-as.character(return_money1)
      #return_money2
      loanMoneyPart2<-as.numeric(loanMoneyPart2)
      return_money2<-round(loanMoneyPart2,4)
      return_money1<-as.character(return_money1)
      #successful_or_not<-0
      successful_or_not<-as.character(successful_or_not)
      #return_date1
      return_begining_date1<-return_date$expected_return_date[2]
      return_begining_date1<-as.character(return_begining_date1)
      return_ending_date1<-return_date$expected_return_date[3]-1
      return_ending_date1<-as.character(return_ending_date1)
      #return_date2
      return_begining_date2<-return_date$expected_return_date[3]
      return_begining_date2<-as.character(return_begining_date2)
      return_ending_date2<-return_date$expected_return_date[4]-1
      return_ending_date2<-as.character(return_ending_date2)
      result<-paste(user_id,application_money,return_begining_date1,return_ending_date1,return_money1,return_begining_date2,
                    return_ending_date2,return_money2,successful_or_not,sep = ",",collapse=",") 
      return(result)
      
    }
    
    
  }
  
  
}


