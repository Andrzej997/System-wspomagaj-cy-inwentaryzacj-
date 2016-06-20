package pl.polsl.reservations.client.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import pl.polsl.reservations.client.ClientContext;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.mediators.AddEditDepartamentMediator;
import pl.polsl.reservations.client.mediators.AddEditInstituteMediator;
import pl.polsl.reservations.client.mediators.AddEditReservationTypeMediator;
import pl.polsl.reservations.client.mediators.AddEditRoomTypeViewMediator;
import pl.polsl.reservations.client.mediators.AddEditUserViewMediator;
import pl.polsl.reservations.client.mediators.AddEditViewMediator;
import pl.polsl.reservations.client.mediators.AssignRoomMediator;
import pl.polsl.reservations.client.mediators.AssignToUnitMediator;
import pl.polsl.reservations.client.mediators.ChangePasswordViewMediator;
import pl.polsl.reservations.client.mediators.CreateReportViewMediator;
import pl.polsl.reservations.client.mediators.LoginMediator;
import pl.polsl.reservations.client.mediators.MainViewMediator;
import pl.polsl.reservations.client.reports.DocumentGenerator;
import pl.polsl.reservations.client.views.utils.AddTypeEnum;
import pl.polsl.reservations.client.views.utils.AddUserEnum;
import pl.polsl.reservations.client.views.utils.FrameStyle;
import pl.polsl.reservations.client.views.utils.MessageBoxUtils;

public class MainView extends JFrame {

    private static final long serialVersionUID = 2541836851186221686L;

    private MainView window;
    private boolean isLoggedIn = false;

    private JMenuItem aboutMenuItem;
    private JMenu addMenuItem;
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
    private JMenuItem editRoomEquipmentMenuItem;
    private JMenuItem roomRaportMenuItem;
    private JMenuItem departmentRaportMenuItem;
    private JMenuItem editUserAdminMenuItem;
    private JMenuItem addRoomTypeMenuItem;
    private JMenuItem addReservationTypeMenuItem;
    private JMenuItem addEditInstituteMenuItem;
    private JMenuItem addEditDepartamentMenuItem;
    private JMenuItem assignRoomMenuItem;
    private JMenuItem assignRoomToDepartamentMenuItem;

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
    private JDialog addRoomTypeFrame;
    private JDialog addStateFrame;
    private JDialog editUserFrame;
    private JDialog tutorialFrame;
    private JDialog addEditReservationTypeFrame;
    private JDialog addEditInstituteFrame;
    private JDialog addEditDepartamentFrame;
    private JDialog assignRoomFrame;
    private JDialog assignRoomToDepartamentFrame;

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

    public void setOptionsAvailable(boolean available) {
        createRaportMenu.setVisible(available);
        generateMenu.setVisible(available);
        adminMenu.setVisible(available);
        accountMenu.setVisible(available);
        addMenuItem.setVisible(available);
        logoutMenuItem.setVisible(available);
    }

    private void initComponents() {
        initFields();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        contentView.setPreferredSize(new java.awt.Dimension(800, 600));
        generateMenuBarElements();
        setJMenuBar(menuBar);
        pack();
        this.addWindowListener(new WindowAdapterImpl());
    }

    private void initFields() {
        contentView = new JPanel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        adminMenu = new JMenu();
        addMenuItem = new JMenu();
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
        addRoomTypeMenuItem = new JMenuItem();
        addDeviceMenuItem = new JMenuItem();
        editUserAdminMenuItem = new JMenuItem();
        editRoomEquipmentMenuItem = new JMenuItem();
        addStateMenuItem = new JMenuItem();
        addTypeMenuItem = new JMenuItem();
        allRaportMenuItem = new JMenuItem();
        departmentRaportMenuItem = new JMenuItem();
        roomRaportMenuItem = new JMenuItem();
        addReservationTypeMenuItem = new JMenuItem();
        addEditInstituteMenuItem = new JMenuItem();
        addEditDepartamentMenuItem = new JMenuItem();
        assignRoomMenuItem = new JMenuItem();
        assignRoomToDepartamentMenuItem = new JMenuItem();
    }

    private void tutorialMenuItemActionPerformed(ActionEvent evt) {
        tutorialFrame = FrameStyle.dialogStyle(new TutorialView(this), "Tutorial");
    }

    private void exitMenuItemActionPerformed(ActionEvent evt) {
        Lookup.removeUserCertificate();
        System.exit(0);
    }

    private void addMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            setView(new AddEditViewMediator().createView(this, false));
        }
    }

    private void addRoomMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            addRoomFrame = FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, AddTypeEnum.ROOM), "Add room");
        }
    }

    private void addDeviceMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            addDeviceFrame = FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, AddTypeEnum.DEVICE), "Add device");
        }
    }

    private void editRoomEquipmentMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            addDeviceFrame = FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, AddTypeEnum.DEVICE_EDIT), "Edit device");
        }
    }

    private void assignUserToRoomMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            assignRoomFrame = FrameStyle.dialogStyle(new AssignRoomMediator().createView(this), "Assign user to room");
        }
    }

    private void assignRoomToDepartamentMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            assignRoomToDepartamentFrame = FrameStyle.dialogStyle(new AssignToUnitMediator().createView(this), "Assing room to departament");
        }
    }

    private void addStateMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            addStateFrame = FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, AddTypeEnum.STATE), "Add state");
        }
    }

    private void addTypeMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            addTypeFrame = FrameStyle.dialogStyle(new CreateReportViewMediator().createView(this, AddTypeEnum.TYPE), "Add type");
        }
    }

    private void addRoomTypeMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            addRoomTypeFrame = FrameStyle.dialogStyle(new AddEditRoomTypeViewMediator().createView(this), "Add/Edit room type");
        }
    }

    private void addReservationTypeMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            addEditReservationTypeFrame = FrameStyle.dialogStyle(new AddEditReservationTypeMediator().createView(this), "Add/Edit room type");
        }
    }

    private void addInstituteMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            addEditInstituteFrame = FrameStyle.dialogStyle(new AddEditInstituteMediator().createView(this), "Add/Edit institutes");
        }
    }

    private void addDepartamentMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            addEditDepartamentFrame = FrameStyle.dialogStyle(new AddEditDepartamentMediator().createView(this), "Add/Edit departaments");
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
        if (isLoggedIn) {
            passwordFrame = FrameStyle.dialogStyle(new ChangePasswordViewMediator().createView(this), "Change password");
        }
    }

    private void addUserActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("ADMIN")) {
                if (!ClientContext.getInstance().canRequestLevel("ADMIN")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                addUserFrame = FrameStyle.dialogStyle(new AddEditUserViewMediator().createView(this, AddUserEnum.ADD), "Add user");
            }
        }
    }

    private void editUserActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            editUserFrame = FrameStyle.dialogStyle(new AddEditUserViewMediator().createView(this, AddUserEnum.EDIT), "Edit user");
        }
    }

    private void editUserAdminActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("ADMIN")) {
                if (!ClientContext.getInstance().canRequestLevel("ADMIN")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                editUserFrame = FrameStyle.dialogStyle(new AddEditUserViewMediator().createView(this, AddUserEnum.ADMIN), "Edit user");
            }
        }
    }

    public void logoutMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            setOptionsAvailable(false);
            ClientContext.getInstance().logout();
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

    private void generateMenuBarElements() {
        fileMenu.setText("File");
        logoutMenuItem.setMnemonic('a');
        logoutMenuItem.setText("Logout");
        fileMenu.add(logoutMenuItem);
        exitMenuItem.setText("Exit");
        fileMenu.add(exitMenuItem);

        addMenuItem.setText("Add reservation");

        createRaportMenu.setText("Rooms and equipment");
        addRoomMenuItem.setText("Add room");
        createRaportMenu.add(addRoomMenuItem);
        addDeviceMenuItem.setText("Add equipment");
        createRaportMenu.add(addDeviceMenuItem);
        editRoomEquipmentMenuItem.setText("Edit equipment");
        createRaportMenu.add(editRoomEquipmentMenuItem);
        assignRoomMenuItem.setText("Assign user to room");
        createRaportMenu.add(assignRoomMenuItem);
        assignRoomToDepartamentMenuItem.setText("Assign room to department");
        createRaportMenu.add(assignRoomToDepartamentMenuItem);

        allRaportMenuItem.setText("All rooms report");
        departmentRaportMenuItem.setText("Department rooms report");
        roomRaportMenuItem.setText("Own room report");
        generateMenu.setText("Generate report");
        generateMenu.add(roomRaportMenuItem);
        generateMenu.add(departmentRaportMenuItem);
        generateMenu.add(allRaportMenuItem);

        adminMenu.setText("Admin actions");
        addUserMenuItem.setText("Add user");
        adminMenu.add(addUserMenuItem);
        editUserAdminMenuItem.setText("Edit users");
        adminMenu.add(editUserAdminMenuItem);
        addEditInstituteMenuItem.setText("Manage institutes");
        adminMenu.add(addEditInstituteMenuItem);
        addEditDepartamentMenuItem.setText("Manage departaments");
        adminMenu.add(addEditDepartamentMenuItem);
        addStateMenuItem.setText("Add equipment state");
        adminMenu.add(addStateMenuItem);
        addTypeMenuItem.setText("Add equipment type");
        adminMenu.add(addTypeMenuItem);
        addRoomTypeMenuItem.setText("Manage room types");
        adminMenu.add(addRoomTypeMenuItem);
        addReservationTypeMenuItem.setText("Manage reservation types");
        adminMenu.add(addReservationTypeMenuItem);

        accountMenu.setText("My account");
        accountMenu.add(changePasswordItem);
        changePasswordItem.setText("Change password");
        editDataMenuItem.setText("Edit user data");
        accountMenu.add(editDataMenuItem);

        helpMenu.setText("Help");
        tutorialMenuItem.setText("Tutorial");
        helpMenu.add(tutorialMenuItem);
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        setOptionsAvailable(false);

        menuBar.add(fileMenu);
        menuBar.add(addMenuItem);
        menuBar.add(createRaportMenu);
        menuBar.add(generateMenu);
        menuBar.add(adminMenu);
        menuBar.add(accountMenu);
        menuBar.add(helpMenu);

        addMenuListeners();
    }

    private void addMenuListeners() {
        allRaportMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("INSTITUTE_CHIEF")) {
                if (!ClientContext.getInstance().canRequestLevel("INSTITUTE_CHIEF")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                generateMenuItemActionPerformed(evt);
            }
        });
        roomRaportMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("TECHNICAL_CHIEF")) {
                if (!ClientContext.getInstance().canRequestLevel("TECHNICAL_CHIEF")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                generateMenuItemActionPerformed(evt);
            }
        });
        departmentRaportMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("DEPARTAMENT_CHIEF")) {
                if (!ClientContext.getInstance().canRequestLevel("DEPARTAMENT_CHIEF")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                generateMenuItemActionPerformed(evt);
            }
        });
        addMenuItem.addMouseListener(new MouseListenerImpl());
        addRoomMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("INSTITUTE_CHIEF")) {
                if (!ClientContext.getInstance().canRequestLevel("INSTITUTE_CHIEF")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                addRoomMenuItemActionPerformed(evt);
            }
        });
        addDeviceMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("TECHNICAL_CHIEF")) {
                if (!ClientContext.getInstance().canRequestLevel("TECHNICAL_CHIEF")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                addDeviceMenuItemActionPerformed(evt);
            }
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
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("ADMIN")) {
                if (!ClientContext.getInstance().canRequestLevel("ADMIN")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                addTypeMenuItemActionPerformed(evt);
            }
        });
        addRoomTypeMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("ADMIN")) {
                if (!ClientContext.getInstance().canRequestLevel("ADMIN")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                addRoomTypeMenuItemActionPerformed(evt);
            }
        });
        addReservationTypeMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("ADMIN")) {
                if (!ClientContext.getInstance().canRequestLevel("ADMIN")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                addReservationTypeMenuItemActionPerformed(evt);
            }
        });
        addEditInstituteMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("ADMIN")) {
                if (!ClientContext.getInstance().canRequestLevel("ADMIN")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                addInstituteMenuItemActionPerformed(evt);
            }
        });
        addEditDepartamentMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("ADMIN")) {
                if (!ClientContext.getInstance().canRequestLevel("ADMIN")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                addDepartamentMenuItemActionPerformed(evt);
            }
        });
        addStateMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("ADMIN")) {
                if (!ClientContext.getInstance().canRequestLevel("ADMIN")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                addStateMenuItemActionPerformed(evt);
            }
        });
        editRoomEquipmentMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("TECHNICAL_WORKER")) {
                if (!ClientContext.getInstance().canRequestLevel("TECHNICAL_WORKER")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                editRoomEquipmentMenuItemActionPerformed(evt);
            }
        });
        assignRoomMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("DEPARTAMENT_CHIEF")) {
                if (!ClientContext.getInstance().canRequestLevel("DEPARTAMENT_CHIEF")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                assignUserToRoomMenuItemActionPerformed(evt);
            }
        });
        assignRoomToDepartamentMenuItem.addActionListener((ActionEvent evt) -> {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("INSTITUTE_CHIEF")) {
                if (!ClientContext.getInstance().canRequestLevel("INSTITUTE_CHIEF")) {
                    MessageBoxUtils.createPrivilegeErrorPane(fileMenu);
                } else {
                    MessageBoxUtils.createPrivilegeError(fileMenu);
                }
            } else {
                assignRoomToDepartamentMenuItemActionPerformed(evt);
            }
        });
        editUserAdminMenuItem.addActionListener((ActionEvent evt) -> {
            editUserAdminActionPerformed(evt);
        });
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

    public JMenu getAddMenuItem() {
        return addMenuItem;
    }

    public void setAddMenuItem(JMenu addMenu) {
        this.addMenuItem = addMenu;
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

    public JDialog getTutorialFrame() {
        return tutorialFrame;
    }

    public void setTutorialFrame(JDialog tutorialFrame) {
        this.tutorialFrame = tutorialFrame;
    }

    public JDialog getAddRoomTypeFrame() {
        return addRoomTypeFrame;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public JMenuItem getAllRaportMenuItem() {
        return allRaportMenuItem;
    }

    public JMenuItem getEditRoomEquipmentMenuItem() {
        return editRoomEquipmentMenuItem;
    }

    public JMenuItem getRoomRaportMenuItem() {
        return roomRaportMenuItem;
    }

    public JMenuItem getDepartmentRaportMenuItem() {
        return departmentRaportMenuItem;
    }

    public JMenuItem getEditUserAdminMenuItem() {
        return editUserAdminMenuItem;
    }

    public JMenuItem getAddRoomTypeMenuItem() {
        return addRoomTypeMenuItem;
    }

    public JMenuItem getAddReservationTypeMenuItem() {
        return addReservationTypeMenuItem;
    }

    public JMenu getAdminMenu() {
        return adminMenu;
    }

    public JMenu getGenerateMenu() {
        return generateMenu;
    }

    public JDialog getAddEditReservationTypeFrame() {
        return addEditReservationTypeFrame;
    }

    public MainViewMediator getMainViewMediator() {
        return mainViewMediator;
    }

    public JMenuItem getAddEditInstituteMenuItem() {
        return addEditInstituteMenuItem;
    }

    public JDialog getAddEditInstituteFrame() {
        return addEditInstituteFrame;
    }

    public JMenuItem getAddEditDepartamentMenuItem() {
        return addEditDepartamentMenuItem;
    }

    public JDialog getAddEditDepartamentFrame() {
        return addEditDepartamentFrame;
    }

    public JMenuItem getAssignRoomMenuItem() {
        return assignRoomMenuItem;
    }

    public JDialog getAssignRoomFrame() {
        return assignRoomFrame;
    }

    public JMenuItem getAssignRoomToDepartamentMenuItem() {
        return assignRoomToDepartamentMenuItem;
    }

    public JDialog getAssignRoomToDepartamentFrame() {
        return assignRoomToDepartamentFrame;
    }

    private static class WindowAdapterImpl extends WindowAdapter {

        public WindowAdapterImpl() {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            Lookup.removeUserCertificate();
            System.exit(0);
        }
    }

    private class MouseListenerImpl implements MouseListener {

        public MouseListenerImpl() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!ClientContext.getInstance().checkUserPrivilegesToAction("TECHNICAL_WORKER")) {
                MessageBoxUtils.createPrivilegeError(MainView.this.addMenuItem);
            } else {
                MainView.this.addMenuItemActionPerformed(null);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

}
