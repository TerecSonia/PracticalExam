package sample;

import java.io.*;
import java.security.ProtectionDomain;
import java.util.ArrayList;

public class Repository {
    ArrayList<Product> products=new ArrayList<>();

    public Repository() {
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
    public Product findById(int id)
    {
        for (int i=0;i<products.size();i++)
        {
            if (products.get(i).getId()==id) {
                Product p = products.get(i);
                return p;
            }
        }
        return null;
    }

    public int add(Product p) {

        products.add(p);
        return 1;

    }

    public void readProducts(String fileName)
    {
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = br.readLine()) != null)
            {
                String[] elems = line.split("[,]");
                if (elems.length < 5)
                    continue;
                Product p = new Product(Integer.parseInt(elems[0].strip()),elems[1].strip(),elems[2].strip(),
                        Double.parseDouble(elems[3].strip()),Integer.parseInt(elems[4].strip()));
                this.products.add(p);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null)
                try {
                    br.close();
                }
                catch (IOException e)
                {
                    System.out.println("Error while closing the file " + e);
                }
        }
    }

    void writeProducts(String filename) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filename));
            for (Object t : products) {
                if (t instanceof Product)
                    bw.write(((Product) t).getId() + "," + ((Product) t).getBrand() + "," + ((Product) t).getName()
               +","+ ((Product) t).getPrice()+","+((Product) t).getQuantity());

                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
