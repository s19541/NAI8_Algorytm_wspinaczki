import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Algorithm {
    int numberOfCities;
    List<CityDistance> cityDistances;
    List<String>cities;
    Algorithm(String fname){
        readDataFromFile(fname);
        Collections.shuffle(cities);
        System.out.println(sequenceToString(findShortestWay(cities)));
    }
    private void readDataFromFile(String fname){
        try{
            BufferedReader bf=new BufferedReader(new FileReader(fname));
            numberOfCities =Integer.parseInt(bf.readLine());
            String line;
            cityDistances=new ArrayList<>();
            cities=new ArrayList<>();
            while((line=bf.readLine())!=null){
                StringTokenizer tokenizer=new StringTokenizer(line," ");
                String city1=tokenizer.nextToken();
                String city2=tokenizer.nextToken();
                cityDistances.add(new CityDistance(city1,city2,Integer.parseInt(tokenizer.nextToken())));
                boolean tmp1=true,tmp2=true;
                for(String city:cities){
                    if(city.equals(city1))
                        tmp1=false;
                    if(city.equals(city2))
                        tmp2=false;
                }
                if(tmp1)
                    cities.add(city1);
                if(tmp2)
                    cities.add(city2);
            }
            bf.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    List<String>findShortestWay(List<String>startingSequence){
        int startingDistance=calcTotalDistance(startingSequence);
        System.out.println("Current best: "+sequenceToString(startingSequence));
        for (int i = 0; i < numberOfCities-1; i++) {
            for (int j = i+1; j < numberOfCities; j++) {
                List<String>neighbor=cloneList(startingSequence);
                neighbor.set(i,startingSequence.get(j));
                neighbor.set(j,startingSequence.get(i));
                System.out.println("checking: "+neighbor);
                if(startingDistance>calcTotalDistance(neighbor))
                    return findShortestWay(neighbor);
            }
        }
        return startingSequence;
    }
    List<String>cloneList(List<String>listToClone){
        List<String>clonedList=new ArrayList<>();
            for(String s:listToClone){
                clonedList.add(s);
            }
        return clonedList;
    }
    int calcTotalDistance(List<String>cities){
        int totalDistance=0;
        for (int i = 0; i < cities.size()-1 ; i++) {
            for(CityDistance cityDistance:cityDistances){
                if((cityDistance.v1.equals(cities.get(i))&&cityDistance.v2.equals(cities.get(i+1)))||(cityDistance.v2.equals(cities.get(i))&&cityDistance.v1.equals(cities.get(i+1))))
                    totalDistance+=cityDistance.d;
            }
        }
        return totalDistance;
    }
    String sequenceToString(List<String>seq){
        String retString="";
        for (int i = 0; i < seq.size(); i++) {
            retString+=seq.get(i);
            if(i!=seq.size()-1)
                retString+="->";
        }
        retString+="    "+calcTotalDistance(seq);
        return retString;
    }
}
