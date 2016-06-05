package pl.polsl.reservations.client.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import pl.polsl.reservations.client.ClientContext;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.mediators.AddEditUserViewMediator;
import pl.polsl.reservations.client.mediators.AddEditViewMediator;
import pl.polsl.reservations.client.mediators.ChangePasswordViewMediator;
import pl.polsl.reservations.client.mediators.CreateReportViewMediator;
import pl.polsl.reservations.client.mediators.LoginMediator;
import pl.polsl.reservations.client.mediators.MainViewMediator;
import pl.polsl.reservations.client.reports.DocumentGenerator;
import pl.polsl.reservations.client.views.utils.FrameStyle;

public class MainView extends JFrame {

    private static final long serialVersionUID = 2541836851186221686L;

    private MainView window;
    private boolean isLoggedIn = false;

    private JMenuItem aboutMenuItem;
    private JMenuItem addMenuItem;
    private JMenuItem logoutMenuItem;
    private JMenuItem tutorialMenuItem;
    private JMenuItem generateMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem changePasswordItem;
    private JMenuItem editDataMenuItem;
    private JMenuItem addUserMenuItem;
    private JMenuItem addRoomMenuItem;
    private JMenuItem addDeviceMenuItem;
    private JMenuItem addStateMenuItem;
    private JMenuItem addTypeMenuItem;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenu accountMenu;
    private JMenu createRaportMenu;
    private JPanel contentView;

    private JMenuBar menuBar;

    private transient final MainViewMediator mainViewMediator;

    public MainView(MainViewMediator mainViewMediator) {
        this.mainViewMediator = mainViewMediator;
        initComponents();

    }

    public void setView(JPanel view) {
        setContentPane(view);
        setVisible(true);
        pack();
        FrameStyle.centreWindow(this);
    }

    public void setOptionsAvailable(Color fg) {
        addMenuItem.setForeground(fg);
        generateMenuItem.setForeground(fg);
        createRaportMenu.setForeground(fg);
        logoutMenuItem.setForeground(fg);
        accountMenu.setForeground(fg);
        changePasswordItem.setForeground(fg);
        editDataMenuItem.setForeground(fg);
        addUserMenuItem.setForeground(fg);
        accountMenu.setForeground(fg);
        addRoomMenuItem.setForeground(fg);
        addDeviceMenuItem.setForeground(fg);
        addStateMenuItem.setForeground(fg);
        addTypeMenuItem.setForeground(fg);
    }

    private void initComponents() {
        initFields();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentView.setPreferredSize(new java.awt.Dimension(800, 600));
        generateMenu();

        menuBar.add(fileMenu);
        generateHelpMenu();

        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        pack();
    }

    private void initFields() {
        contentView = new JPanel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        addMenuItem = new JMenuItem();
        generateMenuItem = new JMenuItem();
        accountMenu = new JMenu();
        logoutMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        helpMenu = new JMenu();
        createRaportMenu = new JMenu();
        tutorialMenuItem = new JMenuItem();
        aboutMenuItem = new JMenuItem();
        changePasswordItem = new JMenuItem();
        addUserMenuItem = new JMenuItem();
        editDataMenuItem = new JMenuItem();
        addRoomMenuItem = new JMenuItem();
        addDeviceMenuItem = new JMenuItem();
        addStateMenuItem = new JMenuItem();
        addTypeMenuItem = new JMenuItem();

    }

    private void tutorialMenuItemActionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not supported yet");
        //  setView(new TutorialView(window));
    }

    private void exitMenuItemActionPerformed(ActionEvent evt) {
        Lookup.removeUserCertificate();
        System.exit(0);
    }

    private void addMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            setView(new AddEditViewMediator().createView(this));
        }
    }

    private void addRoomMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, 1), "Add room");
        }
    }

    private void addDeviceMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, 2), "Add device");
        }
    }

    private void addStateMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, 3), "Add state");
        }
    }

    private void addTypeMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, 4), "Add type");
        }
    }

    private void generateMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(new FileNameExtensionFilter(".pdf", "pdf"));
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                String pathToFile = chooser.getSelectedFile().toString() + ".pdf";
                DocumentGenerator documentGenerator = new DocumentGenerator(pathToFile);
                documentGenerator.generateAllRoomsEquipmentReport();
            }
        }
    }

    private void checkRaportMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            JOptionPane.showMessageDialog(this, "Not supported yet");
            //  setView(new CheckRaportView(window));
        }
    }

    private void changePasswordActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            FrameStyle.dialogStyle(new ChangePasswordViewMediator().createView(this), "Change password");
        }
    }

    private void addUserActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            FrameStyle.dialogStyle(new AddEditUserViewMediator().createView(this, false), "Add user");
        }
    }

    private void editUserActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            FrameStyle.dialogStyle(new AddEditUserViewMediator().createView(this, true), "Edit user");
        }
    }

    private void logoutMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            setOptionsAvailable(Color.gray);
            isLoggedIn = false;
            setView(new LoginMediator().createView(this));
            JOptionPane.showMessageDialog(this, "You are logged out.");
            mainViewMediator.dispatchLogoutMenuItemActionPerformed(evt);
        }
    }

    private void aboutMenuItemActionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Created by:"
                + "\n Aleksandra Bienioszek"
                + "\n Wojciech Dêbski"
                + "\n Pawe³ Janeta"
                + "\n Mateusz Sojka"
                + "\n Krzysztof Strêk"
                + "\n Krzystof Warzecha");
    }

    public void setLogged(boolean value) {
        isLoggedIn = value;
    }

    public void create() {
        setView(new LoginMediator().createView(this));
        setResizable(false);

    }

    private void generateMenu() {
        fileMenu.setText("File");
        addMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        addMenuItem.setText("Add");
        addMenuItem.addActionListener((ActionEvent evt) -> {
            addMenuItemActionPerformed(evt);
        });
        fileMenu.add(addMenuItem);
        generateMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        generateMenuItem.setText("Generate raport");
        generateMenuItem.addActionListener((ActionEvent evt) -> {
            generateMenuItemActionPerformed(evt);
        });
        fileMenu.add(generateMenuItem);

        createRaportMenu.setForeground(new java.awt.Color(153, 153, 153));
        createRaportMenu.setText("Create raport");

        addRoomMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        addRoomMenuItem.setText("Add room");
        addRoomMenuItem.addActionListener((ActionEvent evt) -> {
            addRoomMenuItemActionPerformed(evt);
        });
        createRaportMenu.add(addRoomMenuItem);

        addDeviceMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        addDeviceMenuItem.setText("Add device");
        addDeviceMenuItem.addActionListener((ActionEvent evt) -> {
            addDeviceMenuItemActionPerformed(evt);
        });
        createRaportMenu.add(addDeviceMenuItem);

        //TYLKO ADMIN:
        addStateMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        addStateMenuItem.setText("Add state");
        addStateMenuItem.addActionListener((ActionEvent evt) -> {
            addStateMenuItemActionPerformed(evt);
        });
        createRaportMenu.add(addStateMenuItem);

        //TYLKO ADMIN:
        addTypeMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        addTypeMenuItem.setText("Add type");
        addTypeMenuItem.addActionListener((ActionEvent evt) -> {
            addTypeMenuItemActionPerformed(evt);
        });
        createRaportMenu.add(addTypeMenuItem);

        fileMenu.add(createRaportMenu);

        accountMenu.setForeground(new java.awt.Color(153, 153, 153));
        accountMenu.setText("My account");
        accountMenu.add(changePasswordItem);
        accountMenu.add(addUserMenuItem);
        accountMenu.add(editDataMenuItem);

        changePasswordItem.setText("Change password");
        changePasswordItem.setForeground(new java.awt.Color(153, 153, 153));
        changePasswordItem.addActionListener((ActionEvent evt) -> {
            changePasswordActionPerformed(evt);
        });

        //TYLKO DLA ADMINISTRATORÓW - DODAJ IFA :D
        addUserMenuItem.setText("Add user");
        addUserMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        addUserMenuItem.addActionListener((ActionEvent evt) -> {
            addUserActionPerformed(evt);
        });

        editDataMenuItem.setText("Edit user data");
        editDataMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        editDataMenuItem.addActionListener((ActionEvent evt) -> {
            editUserActionPerformed(evt);
        });

        fileMenu.add(accountMenu);
        logoutMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        logoutMenuItem.setMnemonic('a');
        logoutMenuItem.setText("Logout");
        logoutMenuItem.addActionListener((ActionEvent evt) -> {
            logoutMenuItemActionPerformed(evt);
        });
        fileMenu.add(logoutMenuItem);
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener((ActionEvent evt) -> {
            exitMenuItemActionPerformed(evt);
        });
        fileMenu.add(exitMenuItem);
    }

    public void checkPrivileges() {

        if (!ClientContext.checkUserPrivilegesToAction("ADMIN")) {
            addDeviceMenuItem.setEnabled(false);
            addStateMenuItem.setEnabled(false);
            addTypeMenuItem.setEnabled(false);
            addUserMenuItem.setEnabled(false);
            addRoomMenuItem.setEnabled(false);
        }
        if (!ClientContext.checkUserPrivilegesToAction("TECHNICAL_WORKER")) {
            addMenuItem.setEnabled(false);
            generateMenuItem.setEnabled(false);
            editDataMenuItem.setEnabled(false);
        }
    }

    private void generateHelpMenu() {
        helpMenu.setText("Help");
        tutorialMenuItem.setText("Tutorial");
        tutorialMenuItem.addActionListener((ActionEvent evt) -> {
            tutorialMenuItemActionPerformed(evt);
        });
        helpMenu.add(tutorialMenuItem);

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener((ActionEvent evt) -> {
            aboutMenuItemActionPerformed(evt);
        });
        helpMenu.add(aboutMenuItem);
    }

    public MainView getWindow() {
        return window;
    }

    public void setWindow(MainView window) {
        this.window = window;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public JMenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public void setAboutMenuItem(JMenuItem aboutMenuItem) {
        this.aboutMenuItem = aboutMenuItem;
    }

    public JMenuItem getAddMenuItem() {
        return addMenuItem;
    }

    public void setAddMenuItem(JMenuItem addMenuItem) {
        this.addMenuItem = addMenuItem;
    }

    public JMenuItem getLogoutMenuItem() {
        return logoutMenuItem;
    }

    public void setLogoutMenuItem(JMenuItem logoutMenuItem) {
        this.logoutMenuItem = logoutMenuItem;
    }

    public JMenuItem getTutorialMenuItem() {
        return tutorialMenuItem;
    }

    public void setTutorialMenuItem(JMenuItem tutorialMenuItem) {
        this.tutorialMenuItem = tutorialMenuItem;
    }

    public JMenuItem getGenerateMenuItem() {
        return generateMenuItem;
    }

    public void setGenerateMenuItem(JMenuItem generateMenuItem) {
        this.generateMenuItem = generateMenuItem;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public void setExitMenuItem(JMenuItem exitMenuItem) {
        this.exitMenuItem = exitMenuItem;
    }

    public JMenuItem getChangePasswordItem() {
        return changePasswordItem;
    }

    public void setChangePasswordItem(JMenuItem changePasswordItem) {
        this.changePasswordItem = changePasswordItem;
    }

    public JMenuItem getEditDataMenuItem() {
        return editDataMenuItem;
    }

    public void setEditDataMenuItem(JMenuItem editDataMenuItem) {
        this.editDataMenuItem = editDataMenuItem;
    }

    public JMenuItem getAddUserMenuItem() {
        return addUserMenuItem;
    }

    public void setAddUserMenuItem(JMenuItem addUserMenuItem) {
        this.addUserMenuItem = addUserMenuItem;
    }

    public JMenuItem getAddRoomMenuItem() {
        return addRoomMenuItem;
    }

    public void setAddRoomMenuItem(JMenuItem addRoomMenuItem) {
        this.addRoomMenuItem = addRoomMenuItem;
    }

    public JMenuItem getAddDeviceMenuItem() {
        return addDeviceMenuItem;
    }

    public void setAddDeviceMenuItem(JMenuItem addDeviceMenuItem) {
        this.addDeviceMenuItem = addDeviceMenuItem;
    }

    public JMenuItem getAddStateMenuItem() {
        return addStateMenuItem;
    }

    public void setAddStateMenuItem(JMenuItem addStateMenuItem) {
        this.addStateMenuItem = addStateMenuItem;
    }

    public JMenuItem getAddTypeMenuItem() {
        return addTypeMenuItem;
    }

    public void setAddTypeMenuItem(JMenuItem addTypeMenuItem) {
        this.addTypeMenuItem = addTypeMenuItem;
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public void setFileMenu(JMenu fileMenu) {
        this.fileMenu = fileMenu;
    }

    public JMenu getHelpMenu() {
        return helpMenu;
    }

    public void setHelpMenu(JMenu helpMenu) {
        this.helpMenu = helpMenu;
    }

    public JMenu getAccountMenu() {
        return accountMenu;
    }

    public void setAccountMenu(JMenu accountMenu) {
        this.accountMenu = accountMenu;
    }

    public JMenu getCreateRaportMenu() {
        return createRaportMenu;
    }

    public void setCreateRaportMenu(JMenu createRaportMenu) {
        this.createRaportMenu = createRaportMenu;
    }

    public JPanel getContentView() {
        return contentView;
    }

    public void setContentView(JPanel contentView) {
        this.contentView = contentView;
    }

    public JMenuBar getMenuBarNew() {
        return menuBar;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

}
