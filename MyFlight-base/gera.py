arq = open("airports.dat")

airp = {}

arq.readline()
for line in arq.readlines():
    line = line[:-1]
    data = line.split(";")
    #print(data[0],data[1],data[2],data[3])
    airp[data[0]] = [data[0],data[1],data[2],data[3]]

arq.close()

print(len(airp))

arq2 = open("airports.csv")

arq2.readline()
for line in arq2.readlines():
    line = line[:-1]
    data = line.split(",")
    name = data[3][1:-1]
    country = data[8][1:-1]
    iata = data[13][1:-1]

    if len(iata)>1:
        if iata in airp:
            dat = airp[iata]
            lat = dat[1]
            lon = dat[2]
            print(iata,lat,lon,name,country,sep=";")

arq2.close()

    
