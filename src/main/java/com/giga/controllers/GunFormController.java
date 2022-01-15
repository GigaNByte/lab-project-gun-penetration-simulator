package com.giga.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.giga.App;
import com.giga.HibernateConnection;
import com.giga.model.Context;
import com.giga.model.FireTest;
import com.giga.model.Gun;
import com.giga.model.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.util.ValueHolder;

/**
 * JavaFX Controller for GunForm Tab
 *
 * @author GigaNByte
 * @since 1.0
 */
public class GunFormController implements Initializable {
    @FXML
    private SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
    @FXML
    private TextField gFormName;
    @FXML
    private TextField gFormAmmoName;
    @FXML
    private TextField gFormAmmoType;
    @FXML
    private Spinner<Double> gFormCalliber;
    @FXML
    private TextField gFormNation;
    @FXML
    private Spinner<Double> gFormBarrelLenght;
    @FXML
    private Text gFormErrorMessage;
    @FXML
    private Spinner<Integer> gFormMuzzleVelocity;
    @FXML
    private TableView<Gun> gTable;
    @FXML
    private TableColumn gNameColumn;
    @FXML
    private TableColumn gAmmoNameColumn;
    @FXML
    private TableColumn gNationColumn;
    @FXML
    private TableColumn gAmmoTypeColumn;
    @FXML
    private TableColumn gCaliberColumn;
    @FXML
    private TableColumn gBarrelLenghtColumn;
    @FXML
    private TableColumn gMuzzleVelocityColumn;
    @FXML
    private TableColumn gEdit;
    @FXML
    private TableColumn gDelete;
    @FXML
    private MainController MainController;
    @FXML
    private TextField gFilterField;



    @FXML
    private void addTab(Integer gunIndex) throws IOException {
        Tab singleTab = new Tab("Edit: " + Context.getInstance().getGunTable().get(gunIndex).getGunName());
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/gunEditView.fxml"));
        fxmlLoader.setController(new GunEditController(gunIndex));
        AnchorPane anchorPane = fxmlLoader.load();
        singleTab.setContent(anchorPane);
        MainController.getInstance().addTab(singleTab);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.MainController = MainController.getInstance();
        gNameColumn.setCellValueFactory(new PropertyValueFactory<Gun, String>("gunName"));
        gAmmoNameColumn.setCellValueFactory(new PropertyValueFactory<Gun, String>("ammoName"));
        gNationColumn.setCellValueFactory(new PropertyValueFactory<Gun, String>("nation"));
        gAmmoTypeColumn.setCellValueFactory(new PropertyValueFactory<Gun, String>("ammoType"));
        gCaliberColumn.setCellValueFactory(new PropertyValueFactory<Gun, Double>("caliber"));
        gBarrelLenghtColumn.setCellValueFactory(new PropertyValueFactory<Gun, Double>("barrelLenght"));
        gMuzzleVelocityColumn.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("muzzleVelocity"));
        gEdit.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("id"));
        gDelete.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("id"));

        //Handles Edit and Delete Buttons
        Callback<TableColumn<Gun, Integer>, TableCell<Gun, Integer>> cellEditFactory =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<Gun, Integer> column) {
                        final TableCell<Gun, Integer> cell = new TableCell<Gun, Integer>() {
                            final Button btn = new Button(column.getText());

                            @Override
                            public void updateItem(Integer gunId, boolean empty) {
                                super.updateItem(gunId, empty);
                                if (empty) {
                                    //TODO:add some svg graphic
                                    setGraphic(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        try {
                                            addTab(getIndex());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                        };
                        return cell;
                    }
                };
        Callback<TableColumn<Gun, String>, TableCell<Gun, Integer>> cellDeleteFactory =
                new Callback<TableColumn<Gun, String>, TableCell<Gun, Integer>>() {
                    @Override
                    public TableCell call(final TableColumn<Gun, String> column) {
                        final TableCell<Gun, Integer> cell = new TableCell<Gun, Integer>() {
                            final Button btn = new Button(column.getText());

                            @Override
                            public void updateItem(Integer gunID, boolean empty) {
                                super.updateItem(gunID, empty);
                                if (empty) {
                                    //TODO:add some svg graphic
                                    setGraphic(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        //delete gun
                                        Context.getInstance().deleteGunById(gunID);
                                        //updates gTable
                                        gTable.setItems(Context.getInstance().getGunTable());
                                    });
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                        };
                        return cell;
                    }
                };
        gEdit.setCellFactory(cellEditFactory);
        gDelete.setCellFactory(cellDeleteFactory);

        //speed up spinners
        List<Spinner> Spinners= Arrays.asList(gFormBarrelLenght,gFormCalliber,gFormMuzzleVelocity);
        IncrementHandler handler = new IncrementHandler();
        for (Spinner spinner:Spinners) {
            spinner.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
            spinner.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                Node node = evt.getPickResult().getIntersectedNode();
                if (node.getStyleClass().contains("increment-arrow-button") ||
                        node.getStyleClass().contains("decrement-arrow-button")) {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                }
            });
        }

        //bind sorted table to tableview
        Context.getInstance().getSortedGunTable().comparatorProperty().bind(gTable.comparatorProperty());
        gTable.setItems(Context.getInstance().getFilteredGunTable());

        //adds comparator to sort by column
        //https://stackoverflow.com/questions/17958337/javafx-tableview-with-filteredlist-jdk-8-does-not-sort-by-column
        //https://stackoverflow.com/questions/50109815/javafx-tableview-sort-by-custom-rule-then-by-column-selection
        gTable.sortPolicyProperty().set(new Callback<TableView<Gun>, Boolean>() {
            @Override
            public Boolean call(TableView<Gun> param) {
                final Comparator<Gun> tableComparator = gTable.getComparator();
                // if the column is set to unsorted, tableComparator can be null
                Comparator<Gun> comparator = tableComparator == null ? null : new Comparator<Gun>() {
                    @Override
                    public int compare(Gun o1, Gun o2) {
                        // secondly sort by the comparator that was set for the table
                        return tableComparator.compare(o1, o2);
                    }
                };
                gTable.setItems(Context.getInstance().getFilteredGunTable().sorted(comparator));
                return true;
            }
        });

        gFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            Context.getInstance().getSortedGunTable().comparatorProperty().bind(gTable.comparatorProperty());
            Context.getInstance().getFilteredGunTable().setPredicate(gun -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (gun.getGunName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (gun.getCaliber().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            gTable.setItems(Context.getInstance().getFilteredGunTable());
        });


    }

    /**
     * Submits add gun form
     *
     * @author GigaNByte
     * @since 1.0
     */
    @FXML
    public void submitGunForm() throws IOException {
        if (gFormName.getText().isEmpty()
                || gFormName.getText().isEmpty()
                || gFormAmmoName.getText().isEmpty()
                || gFormAmmoType.getText().isEmpty() ||
                gFormMuzzleVelocity.getValue() <= 0 ||
                gFormCalliber.getValue().doubleValue() <= 0. ||
                gFormBarrelLenght.getValue().doubleValue() <= 0.) {
            gFormErrorMessage.setText("Some fields are missing");
            gFormErrorMessage.setStyle("-fx-text-inner-color: red;");
            gFormErrorMessage.setVisible(true);
        } else {
            //TODO: refactor Gun class
            Gun newGun = new Gun();
            newGun.setGunName(gFormName.getText());
            newGun.setNation(gFormNation.getText());
            newGun.setAmmoName(gFormAmmoName.getText());
            newGun.setAmmoType(gFormAmmoType.getText());
            newGun.setBarrelLenght(gFormBarrelLenght.getValue().doubleValue());
            newGun.setMuzzleVelocity(gFormMuzzleVelocity.getValue());
            newGun.setCaliber(gFormCalliber.getValue().doubleValue());
            newGun.setPen100(0);
            newGun.setPen300(0);
            newGun.setPen500(0);
            newGun.setPen1000(0);
            newGun.setPen1500(0);
            newGun.setPen2000(0);
            newGun.setPen2500(0);
            newGun.setPen3000(0);
            try {
                Context.getInstance().saveOrUpdateEntity(newGun);
                gFormErrorMessage.setStyle("-fx-text-inner-color: green;-fx-text-fill: green;");
                gFormErrorMessage.setText("Added gun successfully");
                gFormErrorMessage.setVisible(true);
                gTable.setItems(Context.getInstance().getGunTable());
            } catch (Exception e) {
                gFormErrorMessage.setStyle("-fx-text-inner-color: red;-fx-text-fill: red;");
                gFormErrorMessage.setText("Error at adding gun");
                gFormErrorMessage.setVisible(true);
                e.printStackTrace();
            }

        }

    }
}
