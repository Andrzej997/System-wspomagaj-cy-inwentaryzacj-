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
import pl.polsl.reservations.client.views.utils.AddTypeEnum;
import pl.polsl.reservations.client.views.utils.FrameStyle;

public class MainView extends JFrame {

    private static final long serialVersionUID = 2541836851186221686L;

    private MainView window;
    private boolean isLoggedIn = false;

    private JMenuItem aboutMenuItem;
    private JMenuItem addMenuItem;
    private JMenuItem logoutMenuItem;
    private JMenuItem tutorialMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem changePasswordItem;
    private JMenuItem editDataMenuItem;
    private JMenuItem addUserMenuItem;
    private JMenuItem addRoomMenuItem;
    private JMenuItem addDeviceMenuItem;
    private JMenuItem addStateMenuItem;
    private JMenuItem addTypeMenuItem;
    private JMenuItem allRaportMenuItem;
    private JMenuItem roomRaportMenuItem;
    private JMenuItem departmentRaportMenuItem;

    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenu accountMenu;
    private JMenu createRaportMenu;
    private JMenu adminMenu;
    private JMenu generateMenu;

    private JPanel contentView;

    private JMenuBar menuBar;

    private JDialog passwordFrame;
    private JDialog addUserFrame;
    private JDialog addRoomFrame;
    private JDialog addDeviceFrame;
    private JDialog addTypeFrame;
    private JDialog addStateFrame;
    private JDialog editUserFrame;

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
        generateMenu.setForeground(fg);
        adminMenu.setForeground(fg);
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
        roomRaportMenuItem.setForeground(fg);
        allRaportMenuItem.setForeground(fg);
        departmentRaportMenuItem.setForeground(fg);

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
        adminMenu = new JMenu();
        addMenuItem = new JMenuItem();
        generateMenu = new JMenu();
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
        allRaportMenuItem = new JMenuItem();
        departmentRaportMenuItem = new JMenuItem();
        roomRaportMenuItem = new JMenuItem();

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
        if (isLoggedIn && addRoomFrame == null) {
            addRoomFrame = FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, AddTypeEnum.ROOM), "Add room");
        }
    }

    private void addDeviceMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn && addDeviceFrame == null) {
            addDeviceFrame = FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, AddTypeEnum.DEVICE), "Add device");
        }
    }

    private void addStateMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn && addStateFrame == null) {
            addStateFrame = FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, AddTypeEnum.STATE), "Add state");
        }
    }

    private void addTypeMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn && addTypeFrame == null) {
            addTypeFrame = FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, AddTypeEnum.TYPE), "Add type");
        }
    }

    private void generateMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(new FileNameExtensionFilter(".pdf", "pdf"));
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION && evt.getSource() instanceof JMenuItem) {
                JMenuItem item = (JMenuItem) evt.getSource();
                if (item.equals(roomRaportMenuItem)) {
                    String pathToFile = chooser.getSelectedFile().toString() + ".pdf";
                    DocumentGenerator documentGenerator = new DocumentGenerator(pathToFile);
                    Integer userRoom = mainViewMediator.getUserRoomNumber();
                    documentGenerator.generateSingleRoomEquipmentReport(userRoom);
                } else if (item.equals(departmentRaportMenuItem)) {
                    String pathToFile = chooser.getSelectedFile().toString() + ".pdf";
                    DocumentGenerator documentGenerator = new DocumentGenerator(pathToFile);
                    String departament = mainViewMediator.getUserDepartament();
                    documentGenerator.generateDepartamentRoomsEquipmentReport(departament);
                } else if (item.equals(allRaportMenuItem)) {
                    String pathToFile = chooser.getSelectedFile().toString() + ".pdf";
                    DocumentGenerator documentGenerator = new DocumentGenerator(pathToFile);
                    documentGenerator.generateAllRoomsEquipmentReport();
                }
            }
        }
    }

    private void changePasswordActionPerformed(ActionEvent evt) {
        if (isLoggedIn && passwordFrame == null) {
            passwordFrame = FrameStyle.dialogStyle(new ChangePasswordViewMediator().createView(this), "Change password");
        }
    }

    private void addUserActionPerformed(ActionEvent evt) {
        if (isLoggedIn && addUserFrame == null) {
            addUserFrame = FrameStyle.dialogStyle(new AddEditUserViewMediator().createView(this, false), "Add user");
        }
    }

    private void editUserActionPerformed(ActionEvent evt) {
        if (isLoggedIn && editUserFrame == null) {
            editUserFrame = FrameStyle.dialogStyle(new AddEditUserViewMediator().createView(this, true), "Edit user");
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
                + "\n Wojciech D�bski"
                + "\n Pawe� Janeta"
                + "\n Mateusz Sojka"
                + "\n Krzysztof Str�k"
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
        addMenuItem.setText("Add reservation");
        fileMenu.add(addMenuItem);
        adminMenu.setText("Admin action");
        adminMenu.setForeground(new java.awt.Color(153, 153, 153));
        fileMenu.add(adminMenu);
        allRaportMenuItem.setText("Full raport");
        departmentRaportMenuItem.setText("Department raport");
        roomRaportMenuItem.setText("Room raport");
        allRaportMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        roomRaportMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        departmentRaportMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        generateMenu.setForeground(new java.awt.Color(153, 153, 153));
        generateMenu.setText("Generate raport");
        generateMenu.add(roomRaportMenuItem);
        generateMenu.add(departmentRaportMenuItem);
        generateMenu.add(allRaportMenuItem);
        fileMenu.add(generateMenu);
        createRaportMenu.setForeground(new java.awt.Color(153, 153, 153));
        createRaportMenu.setText("Create raport");
        addRoomMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        addRoomMenuItem.setText("Add room");
        createRaportMenu.add(addRoomMenuItem);
        addDeviceMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        addDeviceMenuItem.setText("Add device");
        createRaportMenu.add(addDeviceMenuItem);
        //TYLKO ADMIN:
        addStateMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        addStateMenuItem.setText("Add state");
        adminMenu.add(addStateMenuItem);
        //TYLKO ADMIN:
        addTypeMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        addTypeMenuItem.setText("Add type");
        adminMenu.add(addTypeMenuItem);
        fileMenu.add(createRaportMenu);
        accountMenu.setForeground(new java.awt.Color(153, 153, 153));
        accountMenu.setText("My account");
        accountMenu.add(changePasswordItem);
        changePasswordItem.setText("Change password");
        changePasswordItem.setForeground(new java.awt.Color(153, 153, 153));
        //TODO: TYLKO DLA ADMINISTRATOR�W - DODAJ IFA :D
        addUserMenuItem.setText("Add user");
        addUserMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        editDataMenuItem.setText("Edit user data");
        editDataMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        adminMenu.add(addUserMenuItem);
        accountMenu.add(editDataMenuItem);
        fileMenu.add(accountMenu);
        logoutMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        logoutMenuItem.setMnemonic('a');
        logoutMenuItem.setText("Logout");
        fileMenu.add(logoutMenuItem);
        exitMenuItem.setText("Exit");
        fileMenu.add(exitMenuItem);
        addMenuListeners();
    }

    private void addMenuListeners() {
        allRaportMenuItem.addActionListener((ActionEvent evt) -> {
            generateMenuItemActionPerformed(evt);
        });
        roomRaportMenuItem.addActionListener((ActionEvent evt) -> {
            generateMenuItemActionPerformed(evt);
        });
        departmentRaportMenuItem.addActionListener((ActionEvent evt) -> {
            generateMenuItemActionPerformed(evt);
        });
        addMenuItem.addActionListener((ActionEvent evt) -> {
            addMenuItemActionPerformed(evt);
        });
        addRoomMenuItem.addActionListener((ActionEvent evt) -> {
            addRoomMenuItemActionPerformed(evt);
        });
        addDeviceMenuItem.addActionListener((ActionEvent evt) -> {
            addDeviceMenuItemActionPerformed(evt);
        });
        logoutMenuItem.addActionListener((ActionEvent evt) -> {
            logoutMenuItemActionPerformed(evt);
        });
        exitMenuItem.addActionListener((ActionEvent evt) -> {
            exitMenuItemActionPerformed(evt);
        });
        editDataMenuItem.addActionListener((ActionEvent evt) -> {
            editUserActionPerformed(evt);
        });
        addUserMenuItem.addActionListener((ActionEvent evt) -> {
            addUserActionPerformed(evt);
        });
        changePasswordItem.addActionListener((ActionEvent evt) -> {
            changePasswordActionPerformed(evt);
        });
        addTypeMenuItem.addActionListener((ActionEvent evt) -> {
            addTypeMenuItemActionPerformed(evt);
        });
        addStateMenuItem.addActionListener((ActionEvent evt) -> {
            addStateMenuItemActionPerformed(evt);
        });
    }

    public void checkPrivileges() {

        if (!ClientContext.checkUserPrivilegesToAction("ADMIN")) {
            addDeviceMenuItem.setVisible(false);
            addStateMenuItem.setVisible(false);
            addTypeMenuItem.setVisible(false);
            addUserMenuItem.setVisible(false);
            addRoomMenuItem.setVisible(false);
        }
        if (!ClientContext.checkUserPrivilegesToAction("TECHNICAL_WORKER")) {
            addMenuItem.setVisible(false);
            generateMenu.setVisible(false);
            editDataMenuItem.setVisible(false);
        }
    }

    private void generateHelpMenu() {
        helpMenu.setText("Help");
        tutorialMenuItem.setText("Tutorial");
        helpMenu.add(tutorialMenuItem);
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);
        tutorialMenuItem.addActionListener((ActionEvent evt) -> {
            tutorialMenuItemActionPerformed(evt);
        });
        aboutMenuItem.addActionListener((ActionEvent evt) -> {
            aboutMenuItemActionPerformed(evt);
        });
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
        return generateMenu;
    }

    public void setGenerateMenuItem(JMenu generateMenuItem) {
        this.generateMenu = generateMenuItem;
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

    public JDialog getPasswordFrame() {
        return passwordFrame;
    }

    public JDialog getAddUserFrame() {
        return addUserFrame;
    }

    public JDialog getAddRoomFrame() {
        return addRoomFrame;
    }

    public JDialog getAddDeviceFrame() {
        return addDeviceFrame;
    }

    public JDialog getAddTypeFrame() {
        return addTypeFrame;
    }

    public JDialog getAddStateFrame() {
        return addStateFrame;
    }

    public JDialog getEditUserFrame() {
        return editUserFrame;
    }

    public void setPasswordFrame(JDialog passwordFrame) {
        this.passwordFrame = passwordFrame;
    }

    public void setAddUserFrame(JDialog addUserFrame) {
        this.addUserFrame = addUserFrame;
    }

    public void setAddRoomFrame(JDialog addRoomFrame) {
        this.addRoomFrame = addRoomFrame;
    }

    public void setAddDeviceFrame(JDialog addDeviceFrame) {
        this.addDeviceFrame = addDeviceFrame;
    }

    public void setAddTypeFrame(JDialog addTypeFrame) {
        this.addTypeFrame = addTypeFrame;
    }

    public void setAddStateFrame(JDialog addStateFrame) {
        this.addStateFrame = addStateFrame;
    }

    public void setEditUserFrame(JDialog editUserFrame) {
        this.editUserFrame = editUserFrame;
    }

}
