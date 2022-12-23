package org.randomuser;

import com.google.gson.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner {

    public static void main(String[] args) throws Exception {
        System.out.println("***** KULLANICI DATALARI ***********");
        System.out.println("işlem başladı....");
        Scanner sc = null;
        sc = new Scanner(new URL("https://randomuser.me/api/?results=1000").openStream(),"UTF-8");
        StringBuilder sb = new StringBuilder();
        String row;
        while (sc.hasNext()){
            row = sc.nextLine();
            sb.append(row);
        }
        System.out.println("Bütün datalar çekildi.");

        List<Person> kullaniciListesi = new ArrayList<>();
        Gson gson = new Gson();
        /**
         * elimizde bulunan string halindeki json datayı jsonelement olarak parse ediyoruz.
         */
        JsonElement jsonElement = JsonParser.parseString(sb.toString());
        /**
         * Json elemen i bir jsonDataya çevirmeliyiz ki içinden bilgileri alalım
         */
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        /**
         * Json onject e çevrilen datanını içniden artık istediğimiz key ile gerekli
         * bilgileri çekebiliriz.
         */
        JsonArray jsonArray = jsonObject.get("results").getAsJsonArray();
        int size = jsonArray.size();
        for (int i=0;i<size;i++){
            JsonObject p = jsonArray.get(i).getAsJsonObject();
            Person person = gson.fromJson(p, Person.class);
            kullaniciListesi.add(person);
        }

       // kullaniciListesi.forEach(System.out::println);

        /**
         * Canada da oturanları listele
         */
//        kullaniciListesi.stream()
//                .filter(x-> x.getLocation().getCountry().equals("Canada"))
//                .forEach(System.out::println);
        /**
         * yaşı 20 ile 45 arasında olan kadınların listesini getir.
         */



    }
}
