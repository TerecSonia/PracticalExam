package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Controller {
    private Repository repo;
    private ArrayList<String> shoppingB=new ArrayList<>();
    private double total=0;

    @FXML
    private ListView<String> productsList;

    @FXML
    private TextField stringTextField;

    @FXML
    private TextField lowerLimitTextField;

    @FXML
    private TextField upperLimitTextFiled;

    @FXML
    private ListView<String> shoppingBasket;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Label errorLabel;


    @FXML
    private Label totalPriceLabel;



    public Controller(Repository repo) {
        this.repo = repo;
    }

    public Repository getRepo() {
        return repo;
    }

    @FXML
    public void initialize() {
        ArrayList<Product> products = (ArrayList<Product>) this.repo.getProducts();
        ArrayList<String> strings=new ArrayList<>();
        for (Product p:products)
        {
            if (p.getQuantity()==0)
                strings.add(p.getId()+","+p.getBrand()+","+p.getName()+","+p.getPrice()+",Out of stock");
            else
                strings.add(p.getId()+","+p.getBrand()+","+p.getName()+","+p.getPrice());

        }

        ObservableList<String> obsProducts = FXCollections.observableArrayList(strings);

        this.productsList.setItems(obsProducts);

    }

    @FXML
    void filterButton(Event event) {
        String name=this.stringTextField.getText();
        double lower=Double.parseDouble(this.lowerLimitTextField.getText());
        double upper=Double.parseDouble(this.upperLimitTextFiled.getText());

        ArrayList<Product> products = this.getRepo().getProducts();
        ArrayList<Product> filteredProducts=new ArrayList<>();
        if (name==null)
        {
            filteredProducts = (ArrayList<Product>) products.stream()
                    .filter(p ->p.getPrice() >= lower && p.getPrice() <= upper)
                    .collect(Collectors.toList());
        }
        else
            if (Double.toString(lower)==null)
            {
                filteredProducts = (ArrayList<Product>) products.stream()
                        .filter(p -> p.getName().contains(name)  && p.getPrice() <= upper)
                        .collect(Collectors.toList());
            }
            else
                if (Double.toString(upper)==null)
                {
                    filteredProducts = (ArrayList<Product>) products.stream()
                            .filter(p -> p.getName().contains(name) && p.getPrice() >= lower)
                            .collect(Collectors.toList());
                }
                else
                {
                    filteredProducts = (ArrayList<Product>) products.stream()
                            .filter(p -> p.getName().contains(name) && p.getPrice() >= lower && p.getPrice() <= upper)
                            .collect(Collectors.toList());
                }


        ArrayList<String> strings=new ArrayList<>();
        for (Product p:filteredProducts)
        {
            if (p.getQuantity()==0)
                strings.add(p.getId()+","+p.getBrand()+","+p.getName()+","+p.getPrice()+",Out of stock");
            else
                strings.add(p.getId()+","+p.getBrand()+","+p.getName()+","+p.getPrice());

        }

        ObservableList<String> obsProducts = FXCollections.observableArrayList(strings);

        this.productsList.setItems(obsProducts);

    }

    @FXML
    void buyButton(Event event) {
        int quantity=Integer.parseInt(this.quantityTextField.getText());
        String product=this.productsList.getSelectionModel().getSelectedItem();
        String[] elems = product.split("[,]");
        int id=Integer.parseInt(elems[0]);
        for (Product p:this.repo.getProducts())
        {
            if (p.getId()==id)
            {
                if (p.getQuantity()<quantity)
                    this.errorLabel.setText("Quantity unavailable");
                else
                {
                    double total=p.getPrice()*quantity;
                    p.setQuantity(p.getQuantity()-quantity);
                    this.total=this.total+total;
                    this.totalPriceLabel.setText(Double.toString(this.total));
                    shoppingB.add(product);
                    ObservableList<String> obsProducts = FXCollections.observableArrayList(shoppingB);

                    this.shoppingBasket.setItems(obsProducts);

                }
            }



        }
        this.initialize();
        this.repo.writeProducts("src/products.txt");

    }

}
