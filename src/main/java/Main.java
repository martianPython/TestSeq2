import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String inPutDirSAT1A ="/home/user/WIMS/1A/";
        String inPutDirSAT1G ="/home/koushik/Desktop/WIMS/1G/";

      //  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy_MM-_dd_HH_mm_ss");

        /*for (int i =0;i<25;i++){
           System.out.println("Added  "+dateFormatter.format(new Date()) + "   " +i);
           stringList.add(dateFormatter.format(new Date()));
            sleepDelay(1000);
        } */
        Collection<File> sat1GWimsFiles =  fetchFilesFromDirectory(new File(inPutDirSAT1G));
        List<File> sat1AWimsFilesList = new ArrayList<>(sat1GWimsFiles);
       // sat1AWimsFilesList.stream().forEach(System.out::println);
        System.out.println(indexArrayElementChange(sat1AWimsFilesList));
        List<List<File>> fileOfFiles = fetchListOfMessageIDSequenceNumberFile(sat1AWimsFilesList);
        fileOfFiles.forEach(list -> {
            System.out.println("========================");
            list.forEach(System.out::println);
            System.out.println("========================");
        });

    }
    private static List<List<File>> fetchListOfMessageIDSequenceNumberFile(List<File> sat1AWimsFilesList){
        List<Integer> indexChangeList = indexArrayElementChange(sat1AWimsFilesList);
        List<String> stringList = sat1AWimsFilesList.stream().map(File::getName).collect(Collectors.toList());
        List<List<File>> listOfListOfSec = new ArrayList<List<File>>();
        for (int index =0;index<=indexChangeList.size()-1;index++){
            if(index==indexChangeList.size()-1){
                listOfListOfSec.add(sat1AWimsFilesList.subList(indexChangeList.get(index),sat1AWimsFilesList.size()-1));
            }else if (indexChangeList.get(index) + 1 == indexChangeList.get(index + 1)){
                // System.out.println(((sat1AWimsFilesList.get(indexChangeList.get(index)))));
                listOfListOfSec.add(Collections.singletonList(sat1AWimsFilesList.get(indexChangeList.get(index))));
            }
            else{
                listOfListOfSec.add(sat1AWimsFilesList.subList(indexChangeList.get(index),indexChangeList.get(index+1)));
            }

        }
        listOfListOfSec.removeIf(List::isEmpty);


        return listOfListOfSec;
    }

private static List<Integer> indexArrayElementChange(List<File> fileList){
        String val="";List<Integer> indexOfSequenceNumberALterList = new ArrayList<Integer>();
        for (int index=0;index<fileList.size()-1;index++){
            if(! ((fileList.get(index).getName().split("_")[2]).concat("_").concat((fileList.get(index).getName().split("_")[3])).equalsIgnoreCase(val))){
              //  System.out.println((fileList.get(index).getName().split("_")[2]).concat("_").concat((fileList.get(index).getName().split("_")[3])));
               indexOfSequenceNumberALterList.add(index);
            }
            val=(fileList.get(index).getName().split("_")[2]).concat("_").concat((fileList.get(index).getName().split("_")[3]));
        }
        return indexOfSequenceNumberALterList;
}
    public static    void sleepDelay(long pollDelay){
        try {
            Thread.sleep(pollDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static Collection<File> fetchFilesFromDirectory(File directory){
        return FileUtils.listFiles(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
    }
}
