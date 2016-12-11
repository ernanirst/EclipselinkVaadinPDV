/**
 * Single Page App. Each method of this class returns a layout that can be 
 * applicable to the page.
 */

package uppointspdv.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import java.util.List;
import uppointspdv.dao.PDVDao;
import uppointspdv.dao.UserPDVDao;
import uppointspdv.model.PDV;
import uppointspdv.model.UserPDV;


@Theme("mytheme")
public class SinglePageApp extends UI {
    
    /**
    * Login Page. User entry point. Allow users ti create PDV users and to login
    * into the system.
    */
    private VerticalLayout LoginPage(){
        final VerticalLayout layout = new VerticalLayout();
        final TextField username = new TextField("Usuário:");
        final PasswordField password = new PasswordField("Senha:");
        //username.setCaption("Usuário:");

        Button buttonSignIn = new Button("Login");
        Button buttonSignUp = new Button("Cadastro");
        
        // login button
        buttonSignIn.addClickListener( e -> {
            // validate parameters
            if(username.getValue().length() > 2 && password.getValue().length() > 2){
                UserPDVDao userPDVDao = new UserPDVDao("uppointspdv.main");
                boolean logged = userPDVDao.login(new UserPDV(username.getValue(), password.getValue()));
                if(logged){
                    // login succed
                    Notification notif = new Notification("Login bem sucedido");
                    notif.setDelayMsec(1500);
                    notif.show(Page.getCurrent());
                    // now go to other page...
                    setContent(ListPDVsPage());
                }else{
                    // login failed
                    Notification notif = new Notification("Credenciais inválidas");
                    notif.setDelayMsec(1500);
                    notif.show(Page.getCurrent());
                }
            }else{
                // not valid combination
                Notification notif = new Notification("Usuário e senha possuem no mínimo 3 letras");
                notif.setDelayMsec(1500);
                notif.show(Page.getCurrent());
            }
        });

        buttonSignUp.addClickListener( e -> {
            // validate parameters
            if(username.getValue().length() > 2 && password.getValue().length() > 2){
                UserPDVDao userPDVDao = new UserPDVDao("uppointspdv.main");
                boolean created = userPDVDao.createUser(new UserPDV(username.getValue(), password.getValue()));
                if(created){
                    Notification notif = new Notification("Usuário criado com sucesso, agora faça o login");
                    notif.setDelayMsec(1500);
                    notif.show(Page.getCurrent());
                }else{
                    // not valid combination
                    Notification notif = new Notification("Já existe um usário com este nome ou o servidor não foi corretamente configurado");
                    notif.setDelayMsec(1500);
                    notif.show(Page.getCurrent());
                }
            }else{
                // not valid combination
                Notification notif = new Notification("Usuário e senha possuem no mínimo 3 letras");
                notif.setDelayMsec(1500);
                notif.show(Page.getCurrent());
            }
             
         });
        
        layout.addComponents(username, password, buttonSignIn, buttonSignUp);
        layout.setMargin(true);
        layout.setSpacing(true);
        return layout;
    }
    
    /**
    * List of PDVs Page. Shows all PDVs in the system, also allows users to 
    * LogOut and add new PDVs.
    */
    private VerticalLayout ListPDVsPage(){
        final VerticalLayout layout = new VerticalLayout();
               
        // log out button
        Button buttonLogOut = new Button("Logout");
        buttonLogOut.addClickListener( e -> {
            setContent(LoginPage());
        });
        
        // table
        BeanItemContainer<PDV> pdv_list = new BeanItemContainer<>(PDV.class);
                // get all PDVs
            PDVDao pDVDao = new PDVDao("uppointspdv.main");
            List<PDV> pdvs = pDVDao.getAllPDVs();
            for(PDV pdv: pdvs){
                pdv_list.addBean(pdv);
            }
            
            GeneratedPropertyContainer gpc =
                new GeneratedPropertyContainer(pdv_list);
            
            // remove not necessary column
            gpc.removeContainerProperty("id");
            
            // Generate button caption column
            gpc.addGeneratedProperty("Visualizar",
                new PropertyValueGenerator<String>() {

                @Override
                public String getValue(Item item, Object itemId,
                                       Object propertyId) {
                    return "Visualizar"; 
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }
            });
            Grid grid = new Grid(gpc);
            
            // rename columns
            grid.getColumn("name").setHeaderCaption("Nome");
            grid.getColumn("address").setHeaderCaption("Endereço");
            grid.getColumn("phone").setHeaderCaption("Telefone");
            grid.getColumn("working_hours").setHeaderCaption("Horário de Atendimento");
            
            // Button Listener
            grid.getColumn("Visualizar")
                .setRenderer(new ButtonRenderer(e -> {
                    // show only one PDV
                    setContent(showOnePDVPage(((PDV) e.getItemId())));
                })
            );
        
        // add new PDV button
        Button buttonNewPDV = new Button("Adicionar Novo");
        buttonNewPDV.addClickListener( (ClickEvent e) -> {
            // create new PDV page
            setContent(createNewPDVPage());
        });

        layout.addComponents(buttonLogOut, grid, buttonNewPDV);
        layout.setMargin(true);
        layout.setSpacing(true);
        return layout;
    }
    
    /**
    * Create new PDV Page. Allow users to create new PDVs. It is also possible
    * to cancel the operation.
    */
    private VerticalLayout createNewPDVPage(){
        final VerticalLayout layout = new VerticalLayout();
        
        Button buttonCancel = new Button("Cancelar");
        buttonCancel.addClickListener((ClickEvent e) -> {
            // operation canceled, go back
            setContent(ListPDVsPage());
        });

        // PDV properties
        TextField nameTextField = new TextField("Nome:");
        TextField phoneTextField = new TextField("Telefone:");
        TextField addressTextField = new TextField("Endereço:");
        TextField working_hoursTextField = new TextField("Horário de Atendimento:");
        
        Button buttonCreatePDV = new Button("Cadastrar");
        buttonCreatePDV.addClickListener((ClickEvent e) -> {
            if(nameTextField.getValue().length() > 0 &&
                    phoneTextField.getValue().length() > 0 &&
                    addressTextField.getValue().length() > 0 &&
                    working_hoursTextField.getValue().length() > 0){
                // all fields ok, store and go back
                PDVDao pDVDao = new PDVDao("uppointspdv.main");
                pDVDao.createPDV(new PDV(nameTextField.getValue(), phoneTextField.getValue(),
                        addressTextField.getValue(), working_hoursTextField.getValue()));
                Notification notif = new Notification("PDV criado com sucesso");
                notif.setDelayMsec(1500);
                notif.show(Page.getCurrent());
                setContent(ListPDVsPage());
            }else{
                // some field is empty
                Notification notif = new Notification("Você esqueceu de preencher algum campo");
                notif.setDelayMsec(1500);
                notif.show(Page.getCurrent());
            }                
        });
        
        layout.addComponents(nameTextField, phoneTextField, addressTextField,
                working_hoursTextField, buttonCreatePDV, buttonCancel);
        layout.setMargin(true);
        layout.setSpacing(true);
        return layout;
    }
    
    /**
    * Show one PDV Page. Show all properties of a PDV in one page. It allow 
    * users to go back too.
    */
    private VerticalLayout showOnePDVPage(PDV pdv){
        final VerticalLayout layout = new VerticalLayout();
        
        layout.addComponent(new Label("Id: " + String.valueOf(pdv.getId())));
        layout.addComponent(new Label("Nome: " + String.valueOf(pdv.getName())));
        layout.addComponent(new Label("Endereço: " + String.valueOf(pdv.getAddress())));
        layout.addComponent(new Label("Telefone: " + String.valueOf(pdv.getPhone())));
        layout.addComponent(new Label("Horário de Atendimento: " + String.valueOf(pdv.getWorking_hours())));
        Button leaveButton = new Button("Voltar");
        leaveButton.addClickListener((ClickEvent e) -> {
            // go back
            setContent(ListPDVsPage());
        });
        layout.addComponent(leaveButton);
        
        
        layout.setMargin(true);
        layout.setSpacing(true);
        return layout;
    }
    
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(LoginPage());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = SinglePageApp.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
