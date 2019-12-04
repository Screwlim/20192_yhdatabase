import sys
import csv

if(len(sys.argv) != 4):
    print("Usage : read.txt out.txt out+ip.txt")
    exit(1)

in_data = open(str(sys.argv[1]), 'r', encoding='utf-8')  # Input 파일 이름 콘솔 입력
out_data = open(str(sys.argv[2]), 'w', encoding='utf-8', newline='')  # Output  파일 이름 콘솔 입력
out_data_ip = open(str(sys.argv[3]), 'w', encoding='utf-8', newline='')  # Output + ip  파일 이름 콘솔 입력

#in_data = open('testdata.txt', 'r', encoding='utf-8')  # Input 파일 지정
#out_data = open('out.txt', 'w', encoding='utf-8', newline='')  # Output  파일 지정
#out_data_ip = open('outip.txt', 'w', encoding='utf-8', newline='')  # Output + ip  파일 지정

rdr_d = csv.reader(in_data)
wr_d = csv.writer(out_data)
wr_di = csv.writer(out_data_ip)

Flag = ['RSTOS0','RSTR','RSTO','OTH','REJ','S0','S1','S2','S3','SF','SH']
label = ['netbios_dgm','netbios_ssn','netbios_ns','remote_job','http_8001','hostnames','uucp_path','http_2784','iso_tsap',
           'csnet_ns','domain_u','ftp_data','http_443','daytime','harvest','discard','netstat','courier','pm_dump',
           'printer','private','sql_net','tftp_u','sunrpc','Z39_50','gopher','domain','finger','klogin','kshell','supdup',
           'systat','telnet','shell','imap4','eco_i','ecr_i','red_i','pop_2','pop_3','login','tim_i','urh_i','urp_i','ntp_u',
           'vmnet','other','whois','time','echo','ldap','link','http','smtp','uucp','auth','nnsp',
           'nntp','name','exec','aol','IRC','X11','bgp','ctf','mtp','rje','ssh','efs','ftp','oth_i']

count = 0

for line in rdr_d:
    if(line[1]=='tcp'): line[1] = 1
    elif(line[1]=='udp'): line[1] = 2
    elif(line[1]=='icmp') : line[1] = 3


    for m in range(71) :
     if(line[2]==label[m]) : line[2]=(m+1)

    for n in range(11):
        if (line[3] == Flag[n]): line[3] = (n+1)



    #wr_d.writerow(line+['1'])  Label
    wr_d.writerow(line[:28])
    wr_di.writerow(line[28:29])




in_data.close()
out_data.close()
out_data_ip.close()


