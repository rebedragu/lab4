package ex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.Serializable;

enum Stare{achizitionat, expus, vandut}
enum Tiparie{Color, AlbNegru}
enum Format{A3, A4}
enum Sistem{linux, windows}

class Magazin implements Serializable
{
    protected String nume;
    protected int nr_inv;
    protected double pret;
    protected String zona;
    protected Stare stare;
    protected String tip;
    public Magazin(String n, int nr, double p, String z, String s, String tip)
    {
        this.nr_inv=nr;
        this.nume=n;
        this.pret=p;
        this.zona=z;
        this.stare= Stare.valueOf(s);
        this.tip=tip;
    }

    public String getNume() {
        return nume;
    }

    public String getTip() {
        return tip;
    }

    public Stare getStare() {
        return stare;
    }

    public void setStare(Stare stare) {
        this.stare = stare;
    }


    public String toString()
    {
        return "\nNume: "+ nume+"\nNumar Inventar: "+nr_inv+"\nPret: "+pret+"\nZona: "+zona+"\nStare: "+stare+ "\nTip: "+tip;
    }
}

class Imprimanta extends Magazin
{
    private int ppm;
    private String rezolutie;
    private int p_car;
    private Tiparie mod;


    public Imprimanta(String n, int nr, double p, String z, String s, String tip, int pp, String r, int p_car, String m)
    {
        super(n, nr, p, z, s, tip);
        this.ppm=pp;
        this.mod= Tiparie.valueOf(m);
        this.p_car=p_car;
        this.rezolutie=r;

    }

    public void setMod(Tiparie mod) {
        this.mod = mod;
    }

    @Override
    public String toString()
    {
        return super.toString()+"\nPagini scrise: "+ppm+"\nRezolutie: "+rezolutie+"\nNumar de pagini: "+p_car+"\nModul de tiparie: "+mod;
    }
}

class Copiatoare extends Magazin
{
    private int p_ton;
    private Format format;

    public Copiatoare(String n, int nr, double p, String z, String s, String tip, int p_ton, String f)
    {
        super(n, nr, p, z, s, tip);
        this.p_ton=p_ton;
        this.format= Format.valueOf(f);
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String toString()
    {
        return super.toString()+"\nNumarul de pagini: "+p_ton+"\nFormat: "+format;
    }
}

class Calcul extends Magazin
{
    private String tip_mon;
    private double vit_proc;
    private int capacitate;
    private Sistem sistem;

    public Calcul(String n, int nr, double p, String z, String s, String tip, String tip_m, double vit, int c, String sis)
    {
        super(n, nr, p, z, s, tip);
        this.capacitate=c;
        this.sistem= Sistem.valueOf(sis);
        this.tip_mon=tip_m;
        this.vit_proc=vit;
    }

    public void setSistem(Sistem sistem) {
        this.sistem = sistem;
    }

    public String toString()
    {
        return super.toString()+"\nMonitor: "+tip_mon+"\nViteza procesor: "+vit_proc+"\nCapacitate: "+capacitate+"\nSistem: "+sistem;
    }
}

public class Main {

    static void scrie(Object o, String fis) {
        try {
            FileOutputStream f = new FileOutputStream(fis);
            ObjectOutputStream oos = new ObjectOutputStream(f);
            oos.writeObject(o);
            oos.close();
            f.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Object citeste(String fis) {
        try {
            FileInputStream f = new FileInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(f);
            Object o=ois.readObject();
            ois.close();
            f.close();
            return o;
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        String nume_fis = "src/ex/electronice.txt";
        BufferedReader flux_in;
        List<Magazin> produse = new ArrayList<>();



        try {
            flux_in = new BufferedReader(new FileReader(nume_fis));
            String linie;

            while ((linie = flux_in.readLine()) != null) {
                String[] cuvinte = linie.split(";");
                if (cuvinte[5].equals("imprimanta")) {
                    Imprimanta i;
                    i = new Imprimanta(cuvinte[0], Integer.parseInt(cuvinte[1]), Double.parseDouble(cuvinte[2]), cuvinte[3], cuvinte[4], cuvinte[5], Integer.parseInt(cuvinte[6]), cuvinte[7], Integer.parseInt(cuvinte[8]), cuvinte[9]);
                    produse.add(i);
                } else if (cuvinte[5].equals("copiator")) {
                    Copiatoare c = new Copiatoare(cuvinte[0], Integer.parseInt(cuvinte[1]), Double.parseDouble(cuvinte[2]), cuvinte[3], cuvinte[4], cuvinte[5], Integer.parseInt(cuvinte[6]), cuvinte[7]);
                    produse.add(c);
                } else {
                    Calcul sis = new Calcul(cuvinte[0], Integer.parseInt(cuvinte[1]), Double.parseDouble(cuvinte[2]), cuvinte[3], cuvinte[4], cuvinte[5], cuvinte[6], Double.parseDouble(cuvinte[7]), Integer.parseInt(cuvinte[8]), cuvinte[9]);
                    produse.add(sis);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int opt = 1;
        while (opt != 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1.Afişarea tuturor echipamentelor");
            System.out.println("2.Afişarea imprimantelor");
            System.out.println("3.Afişarea copiatoarelor");
            System.out.println("4.Afişarea sistemelor de calcul");
            System.out.println("5.Modificarea stării în care se află un echipament");
            System.out.println("6.Setarea unui anumit mod de scriere pentru o imprimantă");
            System.out.println("7.Setarea unui format de copiere pentru copiatoare ");
            System.out.println("8.Instalarea unui anumit sistem de operare pe un sistem de calcul ");
            System.out.println("9.Afişarea echipamentelor vândute ");
            System.out.println("10.Serializare si deserializare ");
            System.out.println("Obtiunea dumneavoastra: ");
            opt = scanner.nextInt();
            String n;
            Stare s;
            switch (opt) {
                case 1:
                    for (Magazin m : produse) {
                        System.out.println(m);
                    }
                    break;
                case 2:
                    for (Magazin m : produse) {
                        if (m.getTip().equals("imprimanta")) {
                            System.out.println(m);
                        }
                    }
                    break;
                case 3:
                    for (Magazin m : produse) {
                        if (m.getTip().equals("copiator")) {
                            System.out.println(m);
                        }
                    }
                    break;
                case 4:
                    for (Magazin m : produse) {
                        if (m.getTip().equals("sistem de calcul")) {
                            System.out.println(m);
                        }
                    }
                    break;
                case 5:
                    scanner.nextLine();
                    System.out.print("Numele echipamentului: ");
                    n = scanner.nextLine();
                    System.out.print("Noua Stare: ");
                    s = Stare.valueOf(scanner.nextLine());
                    for (Magazin m : produse) {
                        if (m.getNume().equals(n)) {
                            m.setStare(s);
                        }
                    }
                    break;
                case 6:
                    scanner.nextLine();
                    Tiparie t;
                    System.out.print("Numele echipamentului: ");
                    n = scanner.nextLine();
                    System.out.print("Noul mod de tiparie: ");
                    t = Tiparie.valueOf(scanner.nextLine());
                    for (Magazin m : produse) {
                        if (m.getNume().equals(n)) {
                            Imprimanta i = (Imprimanta) m;
                            i.setMod(t);
                        }
                    }
                    break;
                case 7:
                    scanner.nextLine();
                    Format f;
                    System.out.print("Numele echipamentului: ");
                    n = scanner.nextLine();
                    System.out.print("Format de copiere: ");
                    f = Format.valueOf(scanner.nextLine());
                    for (Magazin m : produse) {
                        if (m.getNume().equals(n)) {
                            Copiatoare c = (Copiatoare) m;
                            c.setFormat(f);
                        }
                    }
                    break;
                case 8:
                    scanner.nextLine();
                    Sistem sis;
                    System.out.print("Numele echipamentului: ");
                    n = scanner.nextLine();
                    System.out.print("Noul Sistem: ");
                    sis = Sistem.valueOf(scanner.nextLine());
                    for (Magazin m : produse) {
                        if (m.getNume().equals(n)) {
                            Calcul calcul = (Calcul) m;
                            calcul.setSistem(sis);
                        }
                    }
                    break;
                case 9:
                    Stare v = Stare.valueOf("vandut");
                    for (Magazin m : produse) {
                        if (m.getStare() == v) {
                            System.out.println(m.toString());
                        }
                    }
                case 10:
                    scrie(produse, "equipment.bin");
                    produse = (List<Magazin>) citeste("equipment.bin");
                    System.out.println("Colecția a fost salvată în fișierul equipment.bin.");
                    break;

                case 0:
                    System.out.println("Iesire");
                    break;

                default:
                    System.out.println("Opțiune invalidă. Vă rugăm să alegeți o opțiune validă.");
            }

        }
    }
}


