package com.muhammet;
import com.sun.source.tree.Tree;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Runner {


    public static void main(String[] args) {
        /**
         * Kullanıcılardan almış olduğumuz datalar, bir şekilde
         * kayıt altına almalı ve gerekli olduğu zamanlarda tekrar çağırılaak
         * kullanılmalıdır.
         * Dataların kalıcı olarak kayıt edildiği uygulamalar, DataBase adı
         * verilir. bu uygulamalar ile datalar uzun süreli saklanır ve
         * yedeklenirler.
         * dataların ihtiyaca göre işlenlemye ihtiyacı vardır. bu nedenel
         * gerekli olduğunuda farklı sorgularla çağırılmalıdırlar.
         * Örn:
         * - bir kişiye ait sepet bilgisini görmek istiyoruz.
         * - bir kullanıcının son 3 ayda yaptığı alışverilerin toplam tutarının
         * tüm müşteriler içindeki oranına ihtiyacımız olsun.
         * Bu işlemlerin Database üzerinden yapılması, çok fazla istek atmaya
         * ve sistemi yavaşlatmaya neden olabilir.
         * ** Veritabanından gerekli olan bilgiler tek deferde alınarak,
         * Listelerin içerisine konulur.
         * Dikkat!!! ben daha çok listeler ile çalışmalıyım ki daha
         * performanslı kodlar yazabileyim. Ancak burada dikkat edilmesi gereken
         * durum, benim listeleri mükemmel bir şekilde kullanabiliyor olmam gerkli.
         * Stream api, bizim ihtiyacımız olan listlerin aranması, yönetilmesi
         * için gerekli özelleştirilmiş bir sınıftır.
         *
         */
        // count = 0;
        Stream<String> bosStream = Stream.empty(); // boş bir stream oluşturur.
        // count = 1;
        Stream<String> tekilStream = Stream.of("Pazartesi");
        // count = 4;
        Stream<Double> sayilar = Stream.of(22d,1d,11d,2.5d);
        List<String> gunler = List.of("pzt.","sa.","çar.","pe.","cu.","cts.","pz.");

        /**
         * Tüm listelerde stream miras olarak alınmaktadır. bu nedenle tüm listeler stream
         * a dönrülebilir.
         */
        //örn:  gunler.stream()

        /**
         * List -> Stream çevirelim ve içindeki parametreleri dönelim.
         */
        Stream<String> gunlerStream = gunler.stream();
        //gunlerStream.forEach(System.out::println);

        //gunlerStream.forEach(g->System.out.println(g)); sadece tek bir satır kod çalışır.
        gunlerStream.forEach(g->{
            System.out.println("önce yapılacaklar");
            System.out.println(g);
            System.out.println("ek işlemleri");

        });

        /**
         * Sonsuz Streamler
         */
        Stream<Double> rastgeleRandomSayilar = Stream.generate(Math::random);
        //rastgeleRandomSayilar.forEach(System.out::println);
        Stream<Integer> fonksiyonSayilar = Stream.iterate(1, n -> n+2);
        //fonksiyonSayilar.forEach(System.out::println);
        fonksiyonSayilar = Stream.iterate(1,n -> n<20, n -> n+2);
        fonksiyonSayilar.forEach(p-> System.out.println(p));

        /**
         * Methodlar
         * count -> eleman sayısı
         * min,max -> en küçük ve büyük değer
         * foreach -> bileşenlerini dönmek için
         *
         */
        System.out.println("Günler count.....: "+ sayilar.count());
        //System.out.println("Rastgele sonsuz sayı count...:  "+ rastgeleRandomSayilar.count());

        Stream<String> isimler = Stream.of("Ahmet","Can","Kemalettin","Buse");
        //										  2     7            6
        // bir string ifade dizisinde isim uzunluğu en küçük olanı versin
        Optional<String> minIsim = isimler.min((s1,s2) -> s1.length()-s2.length());
        minIsim.ifPresent(System.out::println);

        /**
         *
         *
         */
        String[] harfler = new String[] {"M","u","h","a","m","m","e","t"};
        String isim="";
        for(String harf: harfler)
            isim += harf;
        System.out.println("İsim......: "+ isim);

        Stream<String> streamHarfler = Stream.of("M","u","h","a","m","m","e","t");
        String streamIsim = streamHarfler.reduce("", (ad,harf)-> ad+harf);
        System.out.println("Reduce......: "+ streamIsim);
        /**
         * {3,5,8,2}
         * a-> belli bir başlangıcı olan ve dönen sonucu tutan değer
         * 1,(a,b) -> a*b
         * 1. adımda -> a=1;
         * 2. adımda a*b => 1*3
         * 3. adımda -> a=3;
         * 4. adımda a*b => 3*5;
         * 5. adımda -> a=15;
         */
        Stream<Integer> streamInt = Stream.of(3,5,8,2);
        Integer sonuc = streamInt.reduce(1,(a,b) -> a*b);
        System.out.println("Sonuç....: "+ sonuc);
        Stream<Integer> streamInt1 = Stream.of(3,5,8,2);
        Integer sonuc1 = streamInt1.reduce(1,(a,b) -> a+b);
        System.out.println("Sonuç....: "+ sonuc1);
        /**
         * BinaryOperator
         */
        BinaryOperator<Integer> op = (a,b) -> a*b;
        Stream<Integer> streamInt2 = Stream.of(3,5,8,2);
        streamInt2.reduce(op).ifPresent(System.out::println);
        Stream<Integer> bosDizi = Stream.of(4,3,2);
        bosDizi.reduce(op).ifPresentOrElse(p->{
            System.out.println("Sonuç...: "+ p);
        },()->{
            System.out.println("Herhangi bir sonuç üretilmedi");
        });

        Stream<String> isimDizi = Stream.of("b","a","h","a","r");
        /**
         * collect -> Bir koleksiyonun başka bir koleksiyona dönüştürülmesi işlemini yapar.
         */
        TreeSet<String> setIsim = isimDizi
                .collect(
                        TreeSet::new,
                        TreeSet::add,
                        TreeSet::addAll
                );
        System.out.println("Set isimler....: "+ setIsim); // a,b,h,r

        Stream<String> isimDizisi1 = Stream.of("b","a","h","a","r");
        TreeSet<String> setIsim2 = isimDizisi1.collect(Collectors.toCollection(TreeSet::new));
        System.out.println("set isimler.....: "+ setIsim2);

        /**
         * Stream içinde en önemli kullanımlardan olan filter, istediğimiz lsite içinde
         * belli kriterlere göre filtreleme yap0mamıza olanak tanır.
         */
        Stream<String> urunlistesi = Stream.of("Ütü","Duy","Bilgisayar","Telefon","Televizyon","Buzdolabı","Bulaşık Makinesi");
        // adı t ile başlyan ürünleri getir.
        //urunlistesi.filter(u->u.startsWith("T")).forEach(System.out::println);
        urunlistesi.filter(u->u.length()>10 || u.length()<=5).forEach(System.out::println);

        System.out.println("Ütü".length());

        /**
         * Bir liste içinde tekrar eden kayıtların gösterilmesi istenmiyor ise distinct kullanılır.
         * -> String,Integer, gibi tekli veri tutan tipleri kullanmak anlamalı iken,
         * Sınıf tipinde ve içinde bir çok alanı barındıran nesneleri kullandığımızda işler karışıyor gibi
         * görüne bilir, burada iki nesnenin bire bir aynı olması gerektiğini unutmayınız.
         * user1 -> {id:1, name: "Mehmet", surname: "Yılmaz"}
         * user2-> {id:1, name: "Mehmet", surname: "Yılmaz"}
         * user3 -> {id:2, name: "Mehmet", surname: "Yılmaz"}
         * user1=user2 -> bunu tekil hale getirir.
         * user1=user3 -> bunu tekil hale getirmez.
         */
        Stream<String> musteriListesi = Stream.of("Ali","Deniz","Bahar","Gümüş","Ali","Deniz","Bekir");
        musteriListesi.distinct().forEach(System.out::println);
        /**
         * limit, sonsuz döngülerde işlemin kısıtlı kalmasını sağlamak içni kullanılır.
         * döngü sayısını belirtir.
         * skip, fonksiyonun belli mikatardaki adımlarını atlayarak devam etmesini sağlar.
         * 1. adım -> ilk adımda fonksiyonun sonsuza gittiğini düşünelim. -> 5,10,20,40,80,160,320,640,1280....
         * 2. adım-> limit(6)-> 5,10,20,40,80,160
         * 3. adım -> skip(2) -> 20,40,80,160
         * 4. adım-> limit(3) -> 20,40,80
         */
        Stream<Integer> sayilarI = Stream.iterate(5, s -> s*2);
        sayilarI.limit(6).skip(2).limit(3).forEach(System.out::println);

        /**
         * Mapping
         * Elimizde, bir firmanın günlük ürün satışları ile ilgili bigiler olsuın.
         * A ürünü -> List<Satis> satış listesi şeklinde bilgiler olsun.
         */
        Stream<String> sehirler = Stream.of("Ankara","İstanbul", "İzmit", "Bursa", "Denizli");
        sehirler.map(String::length).forEach(System.out::println);

        List<Urun> AurunSatisListesi = Arrays.asList(new Urun(), new Urun(), new Urun());
        List<Urun> BurunSatisListesi = Arrays.asList(new Urun());
        List<Urun> CurunSatisListesi = Arrays.asList(new Urun(), new Urun(), new Urun(), new Urun());

        Stream<List<Urun>> urunListesi = Stream.of(AurunSatisListesi,BurunSatisListesi, CurunSatisListesi);
        urunListesi.map(List::size).forEach(System.out::println);

        /**
         * Sorting
         * bir liste içinde belli özelliklere göre sıralama işlemler yapabiliriz.
         * sorted() -> a...Z, 0...9
         * sorted(Comparator.reverseOrder()) -> Z...a, 9...0
         */
        Stream<String> isimListesi = Stream.of("Canan","Zeynep","Gümüş","Eren","Ayşe","Tekin");
        isimListesi.sorted().forEach(System.out::println);
        isimListesi = Stream.of("Canan","Zeynep","Gümüş","Eren","Ayşe","Tekin");
        isimListesi.sorted(Comparator.reverseOrder()).forEach(System.out::println);

        /**
         *  Stream -> Collecting Results
         *
         */

        System.out.println("******* Collecting Results *********");
        System.out.println();
        /**
         * var nedir?
         * var -> Java 10 ile birlikte gelen bir değişken tipi tanımlama yöntemidir.
         * var temel olarak herhangi bir tip almaz. siz bu değişken türüne hangi
         * atamayı yaparsanız o türe dönüşür.
         */
        var sayiiii = 45;
        var ifade = "ifade";
        var olduMu = true;
        var sayiListesi = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        var gunListesi = Stream.of("Pazartesi","Salı","Çarşamba","Perşembe","Cuma","Cumartesi","Pazar");
        String sGunler = gunListesi.collect(Collectors.joining("-"));
        System.out.println(sGunler);

        gunListesi = Stream.of("Pazartesi","Sali","Carsamba","Persembe","Cuma","Cumartesi","Pazar");
        Double dGublerAvg = gunListesi.collect(Collectors.averagingDouble(String::length));
        System.out.println("Günlerin ortalama uzunluğu.....: "+dGublerAvg);

        gunListesi = Stream.of("Pazartesi","Sali","Carsamba","Persembe","Cuma","Cumartesi","Pazar");
        TreeSet<String> tsGunler = gunListesi
                .filter(g-> g.startsWith("C"))
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("C ile başlayan günler....: "+tsGunler);
        /**
         * Map<GününAdı,Uzunluğu> -> Salı->4
         */
        gunListesi = Stream.of("Pazartesi","Sali","Carsamba","Persembe","Cuma","Cumartesi","Pazar");
        Map<String,Integer> mapGunler =
                gunListesi.collect(Collectors.toMap(g-> g, String::length));
        //  gunListesi.collect(Collectors.toMap(g-> g, g-> g.startsWith("C") ? g.length() : 0));
        System.out.println("Günlerin uzunlukları....: "+mapGunler);// {pazartesi=9, sali=4, carsamba=8, persembe=8, cuma=4, cumartesi=9, pazar=5}
        /**
         * Key-> Value
         * 4 =["salı","cuma"]
         * 5 =["pazar"]
         * 8 =["carsamba","perşembe"]
         */
        gunListesi = Stream.of("Pazartesi","Sali","Carsamba","Persembe","Cuma","Cumartesi","Pazar");
        Map<Integer,List<String>> mapListGunler =
                gunListesi.collect(Collectors.groupingBy(String::length));
        System.out.println("Günlerin uzunlukları....: "+mapListGunler);// {4=[sali, cuma], 5=[pazar], 8=[carsamba, persembe], 9=[pazartesi, cumartesi]}

        var urunList =
                Stream.of(new Urun("A Marka","Seker"),
                          new Urun("A Marka","Un"),
                        new Urun("B Marka","Yağ"),
                        new Urun("C Marka","Çikolata")
                        );
        Map<String,List<Urun>> markaGrupListesi =
                urunList.collect(Collectors.groupingBy(Urun::getMarka));
        System.out.println("Marka Grup Listesi....: "+markaGrupListesi);

        gunListesi = Stream.of("Pazartesi","Sali","Carsamba","Persembe","Cuma","Cumartesi","Pazar");
        TreeMap<String,List<String>> glist =
                gunListesi.collect(
                        Collectors.groupingBy(
                                g-> g.substring(0,1),
                                TreeMap::new,
                                Collectors.toList()
                        )
                );
        System.out.println("Günlerin uzunlukları....: "+glist);

    }

}
