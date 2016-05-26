package pl.polsl.reservations.client.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.mediators.AccountViewMediator;
import pl.polsl.reservations.client.mediators.AddEditViewMediator;
import pl.polsl.reservations.client.mediators.LoginMediator;
import pl.polsl.reservations.client.mediators.MainViewMediator;

public class MainView extends JFrame {

    private MainView window;
    private boolean isLoggedIn = false;

    private JMenuItem aboutMenuItem;
    private JMenuItem accountMenuItem;
    private JMenuItem addMenuItem;
    private JMenuItem checkRaportMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu fileMenu;
    private JMenuItem generateMenuItem;
    private JMenu helpMenu;
    private JPanel contentView;
    private JMenuItem logoutMenuItem;
    private JMenuBar menuBar;
    private JMenuItem tutorialMenuItem;
    
    private transient final MainViewMediator mainViewMediator;

    public MainView(MainViewMediator mainViewMediator) {
        this.mainViewMediator = mainViewMediator;
        initComponents();

    }

    public void setView(JPanel view) {
        setContentPane(view);
        setVisible(true);
        pack();
    }

    public void setOptionsAvailable(Color fg) {
        addMenuItem.setForeground(fg);
        generateMenuItem.setForeground(fg);
        checkRaportMenuItem.setForeground(fg);
        logoutMenuItem.setForeground(fg);
        accountMenuItem.setForeground(fg);
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
        accountMenuItem = new JMenuItem();
        logoutMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        helpMenu = new JMenu();
        tutorialMenuItem = new JMenuItem();
        aboutMenuItem = new JMenuItem();
        checkRaportMenuItem = new JMenuItem();
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

    private void generateMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
             JOptionPane.showMessageDialog(this, "Not supported yet");
      //      setView(new GenerateRaportView(window));
        }
    }

    private void checkRaportMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
             JOptionPane.showMessageDialog(this, "Not supported yet");
          //  setView(new CheckRaportView(window));
        }
    }

    private void accountMenuItemActionPerformed(ActionEvent evt) {
        if (isLoggedIn) {
            setView(new AccountViewMediator().createView(window));
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
    JOptionPane.showMessageDialog(this, "Not supported yet");   }

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
        generateMenuItem.setText("Generate");
        generateMenuItem.addActionListener((ActionEvent evt) -> {
            generateMenuItemActionPerformed(evt);
        });
        fileMenu.add(generateMenuItem);

        checkRaportMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        checkRaportMenuItem.setText("Check raport");
        checkRaportMenuItem.addActionListener((ActionEvent evt) -> {
            checkRaportMenuItemActionPerformed(evt);
        });
        fileMenu.add(checkRaportMenuItem);

        accountMenuItem.setForeground(new java.awt.Color(153, 153, 153));
        accountMenuItem.setText("My account");
        accountMenuItem.addActionListener((ActionEvent evt) -> {
            accountMenuItemActionPerformed(evt);
        });
        fileMenu.add(accountMenuItem);
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

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public JMenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public JMenuItem getAccountMenuItem() {
        return accountMenuItem;
    }

    public JMenuItem getAddMenuItem() {
        return addMenuItem;
    }

    public JMenuItem getCheckRaportMenuItem() {
        return checkRaportMenuItem;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public JMenuItem getGenerateMenuItem() {
        return generateMenuItem;
    }

    public JMenu getHelpMenu() {
        return helpMenu;
    }

    public JPanel getContentView() {
        return contentView;
    }

    public JMenuItem getLogoutMenuItem() {
        return logoutMenuItem;
    }

    public JMenuBar getMenuBarX() {
        return menuBar;
    }

    public JMenuItem getTutorialMenuItem() {
        return tutorialMenuItem;
    }
        
}
