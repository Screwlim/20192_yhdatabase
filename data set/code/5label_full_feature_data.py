import csv
import pandas as pd

f_major = open('Test1.csv', 'r', encoding='utf-8')  # Input 파일 이름
out_major = open('label_test03.txt', 'w', encoding='utf-8', newline='')  # Output  파일 이름
rdr_m = csv.reader(f_major)
wr_m = csv.writer(out_major)

Flag = ['RSTOS0','RSTR','RSTO','OTH','REJ','S0','S1','S2','S3','SF','SH']
label = ['netbios_dgm','netbios_ssn','netbios_ns','remote_job','http_8001','hostnames','uucp_path','http_2784','iso_tsap',
           'csnet_ns','domain_u','ftp_data','http_443','daytime','harvest','discard','netstat','courier','pm_dump',
           'printer','private','sql_net','tftp_u','sunrpc','Z39_50','gopher','domain','finger','klogin','kshell','supdup',
           'systat','telnet','shell','imap4','eco_i','ecr_i','red_i','pop_2','pop_3','login','tim_i','urh_i','urp_i','ntp_u',
           'vmnet','other','whois','time','echo','ldap','link','http','smtp','uucp','auth','nnsp',
           'nntp','name','exec','aol','IRC','X11','bgp','ctf','mtp','rje','ssh','efs','ftp']

DoS = ['back','land','neptune','pod','smurf','teardrop','apache2','udpstorm','processtable','worm']
Probe = ['satan','ipsweep','nmap','portsweep','mscan','saint']
R2L = ['guess_passwd','ftp_write','imap','phf','multihop','warezmaster','warezclient','spy','xlock','xsnoop','snmpguess','snmpgetattack','httptunnel','sendmail','named']
U2R = ['buffer_overflow','loadmodule','rootkit','perl','sqlattack','xterm','ps']

count=[0,0,0,0,0]


for line in rdr_m:
    if(line[1]=='tcp'): line[1] = 1                     #line[1]  protocol
    elif(line[1]=='udp'): line[1] = 2
    elif(line[1]=='icmp') : line[1] = 3


    for m in range(70) :                                    #line[2] service
     if(line[2]==label[m]) : line[2]=(m+1)

    for n in range(11):
        if (line[3] == Flag[n]): line[3] = (n+1)                #line[3] flag

    for m in range(10):                                        #line[41] label for deeplearning
       if(line[41]==DoS[m]): line[41]=1
    for m in range(6):
       if(line[41]==Probe[m]): line[41]=2
    for m in range(15):
       if(line[41]==R2L[m]): line[41]=3
    for m in range(7):
       if(line[41]==U2R[m]): line[41]=4

    if(line[41]=='normal'): line[41]=0

    if(line[41]==0):
        count[0]= count[0]+1
    if (line[41] == 1):
        count[1] = count[1] + 1
    if (line[41] == 2):
        count[2] = count[2] + 1
    if (line[41] == 3):
        count[3] = count[3] + 1
    if (line[41] == 4):
        count[4] = count[4] + 1

    wr_m.writerow(line[:42])




f_major.close()

print(count)                # number of data

