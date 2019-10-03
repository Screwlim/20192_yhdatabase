import csv
import pandas as pd

f_major = open('Train1.csv', 'r', encoding='utf-8')  # Input 파일 이름
out_major = open('change_train03.txt', 'w', encoding='utf-8', newline='')  # Output  파일 이름
rdr_m = csv.reader(f_major)
wr_m = csv.writer(out_major)

Flag = ['RSTOS0','RSTR','RSTO','OTH','REJ','S0','S1','S2','S3','SF','SH']
label = ['netbios_dgm','netbios_ssn','netbios_ns','remote_job','http_8001','hostnames','uucp_path','http_2784','iso_tsap',
           'csnet_ns','domain_u','ftp_data','http_443','daytime','harvest','discard','netstat','courier','pm_dump',
           'printer','private','sql_net','tftp_u','sunrpc','Z39_50','gopher','domain','finger','klogin','kshell','supdup',
           'systat','telnet','shell','imap4','eco_i','ecr_i','red_i','pop_2','pop_3','login','tim_i','urh_i','urp_i','ntp_u',
           'vmnet','other','whois','time','echo','ldap','link','http','smtp','uucp','auth','nnsp',
           'nntp','name','exec','aol','IRC','X11','bgp','ctf','mtp','rje','ssh','efs','ftp']


for line in rdr_m:
    if(line[1]=='tcp'): line[1] = 1
    elif(line[1]=='udp'): line[1] = 2
    elif(line[1]=='icmp') : line[1] = 3


    for m in range(70) :
     if(line[2]==label[m]) : line[2]=(m+1)

    for n in range(11):
        if (line[3] == Flag[n]): line[3] = (n+1)

    #if(line[41]=='normal'):
    #    line[41]=0
    #else :
    #    line[41]=1


    wr_m.writerow(line[:42])



#dataset = pd.read_csv('./change.csv')
#print(dataset)      #전체 호출

f_major.close()



#Serv = ['netbios_dgm','netbios_ssn','netbios_ns','http_8001','hostnames','ucp_path','http_2784','iso_tsap',
#           'csnet_ns','domain_u','ftp_data','http_443','daytime','harvest','discard','netstat','courier','pm_dump',
#           'printer','private','sql_net','tftp_u','sunrpc','Z39_50','gopher','domain','finger','klogin','kshell,','supdup',
#           'systat','telnet','shell','imap4','eco_i','ecr_i','red_i','pop_2','pop_3','login','tim_i','urh_i','nrp_i','ntp_u',
#           'vmnet','other','whois','time','echo','ldap','link','http','smtp','uucp','auth','nnsp',
#           'nntp','name','exec','aol','IRC','X11','bgp','ctf','mtp','rje','ssh','efs','ftp']