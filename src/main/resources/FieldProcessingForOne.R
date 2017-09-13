FieldProcessingForOne=function(userID,status,username,password,dbname,host,port){
  userID<-as.character(userID)
  status<-as.numeric(status)
  
  if (status == 1){
    
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
      #dbname  = "wf_wish_test",
      #host    = "192.168.1.2"
      #host    = "cskk.f3322.net",
      #port = 85
    )
    
    
    #basic informations
    data1=dbGetQuery(con,'select user_id,order_id,merchant_id,payment_amount,order_date,current_expected_payment_eligibility_date,
                     is_refunded,loan_operation,has_been_disbursed,order_month,amount,loan_period from t_wish_order')
    data1$user_id<-as.character(data1$user_id)
    data1$order_id<-as.character(data1$order_id)
    data1$merchant_id<-as.character(data1$merchant_id)
    data1$payment_amount<-as.numeric(data1$payment_amount)
    data1$order_date<-as.Date(data1$order_date)
    data1$current_expected_payment_eligibility_date<-as.Date(data1$current_expected_payment_eligibility_date)
    data1$is_refunded<-as.numeric(data1$is_refunded)
    data1$loan_operation<-as.numeric(data1$loan_operation)
    data1$has_been_disbursed<-as.numeric(data1$has_been_disbursed)
    data1$order_month<-format(as.Date(data1$order_date),"%Y/%m")
    data1$amount<-as.numeric(data1$amount)
    data1$loan_period<-data1$current_expected_payment_eligibility_date-data1$order_date
    data1$loan_period<-as.numeric(data1$loan_period)
    data1[,'order_year']<-format(as.Date(data1$order_date),"%Y")
    data1[is.na(data1)]<-0
    data1<-data1[!duplicated(data1$order_id),]
    
    data2=dbGetQuery(con,'select user_id,merchant_id from t_merchant')
    
    n1=length(data1$user_id)
    n1[is.na(n1)]<-0
    
    if (n1 > 0){
      
      #apply users' summary
      admittance_operation<-c()
      continuous_operation<-c()
      avg_sales<-c()
      avg_refund_time_rate<-c()
      avg_refund_amount_rate<-c()
      
      data2<-data2[!duplicated(data2$user_id),]
      required_merchant_id<-data2$merchant_id[which(data2$user_id %in% userID)]
      n2=length(required_merchant_id)
      n2[is.na(n2)]<-0
      
      if (n2 > 0){
        
        data<-data1[which(data1$merchant_id %in% required_merchant_id),]
        
        
        n3=length(data$order_date)
        n3[is.na(n3)]<-0
        
        if (n3 > 0){
          
          data[is.na(data)]<-0
          data$order_date<-as.Date(data$order_date)
          min_date<-min(data$order_date)
          max_date<-today
          date_length<-as.numeric(max_date-min_date)
          date_series<-as.data.frame(min_date+days(0:date_length))
          colnames(date_series)<-c("date")
          date_series[,"month"]<-format(date_series$date,"%Y/%m")
          date_series[,"year"]<-format(date_series$date,"%Y")
          
          
          
          #sales summary#
          time<-aggregate(date~month+year,data=date_series,length)
          time<-time[,c("month","year")]
          sales_month<-aggregate(amount~order_month,data=data,sum)
          sales_year<-aggregate(amount~order_year,data=data,sum)
          sales_summary<-merge(time,sales_month,by.x="month",by.y="order_month",all.x=T)
          sales_summary$amount[is.na(sales_summary$amount)]<-0
          colnames(sales_summary)<-c("month","year","sales_month")
          sales_summary<-merge(sales_summary,sales_year,by.x="year",by.y="order_year",all.x=T)
          colnames(sales_summary)<-c("year","month","sales_month","sales_year")
          sales_summary$sales_year[is.na(sales_summary$sales_year)]<-0
          sales_summary<-sales_summary[,c("month","sales_month","sales_year")]
          today_month<-format(today,"%Y/%m")
          sales_summary<-sales_summary[which(sales_summary$month != today_month),]
          sales_summary[,"sales_rate"]<-sales_summary$sales_month/sales_summary$sales_year
          n4=length(sales_summary$month)
          if (n4 > 0){
            sales_summary[,"sales_situa"]<-0
            sales_summary$sales_situa[which(sales_summary$sales_rate >= 0.05)]<-1
            sales_summary$sales_month<-as.numeric(sales_summary$sales_month)
            sales_summary$sales_year<-as.numeric(sales_summary$sales_year)
            sales_summary$sales_rate<-as.numeric(sales_summary$sales_rate)
            sales_summary$sales_situa<-as.numeric(sales_summary$sales_situa)
            #last continuously operating months
            cid=which(sales_summary$sales_situa == 0)
            n=length(cid)
            if (n == 0){
              continuousOperation<-length(sales_summary$month)
            } else if (n == 1){
              continuousOperation<-length(sales_summary$month)-cid[1]
            } else if (n >= 2){
              k<-n
              repeat {
                k<-k-1
                if (sales_summary$sales_situa[cid[k+1]] == 0 & sales_summary$sales_situa[cid[k]] == 0) break}
              continuousOperation<-length(sales_summary$month)-cid[k+1]
            }
            continuousOperation[is.na(continuousOperation)]<-0
          }else if (n4 <= 0){
            continuousOperation<-0
          }
          continuous_operation<-as.numeric(continuousOperation)
          
          
          #operating situation summary#
          #admitted months 
          admittanceOperation<-length(sales_month$order_month)
          admittanceOperation[is.na(admittanceOperation)]<-0
          admittance_operation<-as.numeric(admittanceOperation)
          
          
          
          
          
          #sales situation
          colnames(sales_month)<-c("order_month","sum")
          earlest_month<-min(sales_month$order_month)
          sales_month<-sales_month[which(sales_month$order_month != today_month & sales_month$order_month != earlest_month),]
          n5<-length(sales_month$order_month)
          avgSales<-0
          if (n5 > 0){
            sales_month$sum<-as.numeric(sales_month$sum)
            avgSales<-mean(sales_month$sum,na.rm=T)
          } else if (n5 <= 0){
            avgSales<-0
          }
          avgSales<-as.numeric(avgSales)
          avgSales[is.na(avgSales)]<-0
          avg_sales<-round(avgSales,4)
          
          
          
          #refund situation
          refund_data<-data[which(data$is_refunded==1),]
          #refund time situation
          all_time_summary1<-aggregate(is_refunded~order_month,data=data,length)
          refund_times_summary<-aggregate(is_refunded~order_month,data=refund_data,length)
          refund_times_summary<-merge(refund_times_summary,all_time_summary1,by="order_month",all.x=T)
          colnames(refund_times_summary)<-c("order_month","refund_times","sale_times")
          refund_times_summary$refund_times<-as.numeric(refund_times_summary$refund_times)
          refund_times_summary$sale_times<-as.numeric(refund_times_summary$sale_times)
          n6<-length(refund_times_summary$sale_times)
          avgRefundTimesRate<-0
          if (n6 > 0){
            refund_times_summary[,'refund_times_rate']<-refund_times_summary$refund_times/refund_times_summary$sale_times
            refund_times_summary$refund_times_rate<-as.numeric(refund_times_summary$refund_times_rate)
            today_month<-format(today,"%Y/%m")
            earlest_month<-min(refund_times_summary$order_month)
            refund_times_summary<-refund_times_summary[which(refund_times_summary$order_month != today_month & refund_times_summary$order_month != earlest_month),]
            n7<-length(refund_times_summary$refund_times_rate)
            if (n7 > 0){
              avgRefundTimesRate<-mean(refund_times_summary$refund_times_rate,na.rm=T)
            } else if (n7 <= 0){
              avgRefundTimesRate<-0
            }
          } else if (n6 <= 0){
            avgRefundTimesRate<-0
          }
          avgRefundTimesRate<-as.numeric(avgRefundTimesRate)
          avgRefundTimesRate[is.na(avgRefundTimesRate)]<-0
          avg_refund_time_rate<-round(avgRefundTimesRate,4)
          
          #refund time situation
          all_time_summary2<-aggregate(amount~order_month,data=data,sum)
          refund_amount_summary<-aggregate(amount~order_month,data=refund_data,sum)
          refund_amount_summary<-merge(refund_amount_summary,all_time_summary2,by="order_month",all.x=T)
          colnames(refund_amount_summary)<-c("order_month","refund_amount","sales_amount")
          refund_amount_summary$refund_amount<-as.numeric(refund_amount_summary$refund_amount)
          refund_amount_summary$sales_amount<-as.numeric(refund_amount_summary$sales_amount)
          n8<-length(refund_amount_summary$sales_amount)
          avgRefundAmountRate<-0
          if (n8 > 0){
            refund_amount_summary[,'refund_amount_rate']<-refund_amount_summary$refund_amount/refund_amount_summary$sales_amount
            refund_amount_summary$refund_amount_rate<-as.numeric(refund_amount_summary$refund_amount_rate)
            today_month<-format(today,"%Y/%m")
            earlest_month<-min(refund_amount_summary$order_month)
            refund_amount_summary<-refund_amount_summary[which(refund_amount_summary$order_month != today_month & refund_amount_summary$order_month != earlest_month),]
            n9<-length(refund_amount_summary$refund_amount_rate)
            if (n9 > 0){
              avgRefundAmountRate<-mean(refund_amount_summary$refund_amount_rate,na.rm=T)
            } else if (n9 <= 0){
              avgRefundAmountRate<-0
            }
          } else if (n8 <= 0){
            avgRefundAmountRate<-0
          }
          avgRefundAmountRate<-as.numeric(avgRefundAmountRate)
          avgRefundAmountRate[is.na(avgRefundAmountRate)]<-0
          avg_refund_amount_rate<-round(avgRefundAmountRate,4)
        }
        
        
        continuous_operation_summary<-data.frame(required_merchant_id,userID,admittance_operation,continuous_operation,avg_sales,avg_refund_time_rate,avg_refund_amount_rate)
        colnames(continuous_operation_summary)<-c("merchant_id","user_id","admittance_operation","continuous_operation","avg_sales","avg_refund_time_rate","avg_refund_amount_rate")
        continuous_operation_summary[,"update_time"]<-Sys.time()
        continuous_operation_summary[is.na(continuous_operation_summary)]<-0
        
        
        dbGetQuery(con,"set names utf8")
        dbWriteTable(con,"t_merchant_basic_info",continuous_operation_summary,append=T,row.names=F)
        
      }
    }
    
    dbDisconnect(con)
    
  }
  
}