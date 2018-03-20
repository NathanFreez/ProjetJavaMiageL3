/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

import com.toedter.calendar.JCalendar;
import java.awt.Dialog;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nathan
 */
public class Accueil extends javax.swing.JFrame {

    private HashMap<String, Integer> a;
    private HashMap<String, Mission> hashMapMission;
    private HashMap<String, Competence> hashMapCompetence;
    private HashMap<String, Employe> hashMapEmploye;
    private Entreprise e;
    private DefaultTableModel model2;
    private DefaultTableModel model1;
    private Entreprise ent;
    
    /**
     * Creates new form Accueil
     * @throws java.io.FileNotFoundException
     */
    public Accueil() throws FileNotFoundException {
        initComponents();
        this.ent = new Entreprise();
        Date datAjd = new Date();
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        AffDate.setText(": " + mediumDateFormat.format(datAjd));
        this.e = new Entreprise();
        this.hashMapMission = initialiserHashMapMission();
        this.hashMapCompetence = initialiserHashMapCompetence();
        this.hashMapEmploye = initialiserHashMapEmploye();
        buttonAfficherMissionCompetence.setVisible(false);
        this.model2 = (DefaultTableModel) jTableCompetences.getModel();
        this.model1 = (DefaultTableModel) jTableEmployePropo.getModel();
        this.initialiserComboBox();
        try {
            this.e.mAJDate(this.e, datAjd);
        } catch (IOException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * La fonction permet d'ajouter les noms missions à la comboBox
     */
    public void initialiserComboBox() {
        this.jComboBoxMissions.removeAllItems();
        for (String m : this.hashMapMission.keySet()) {
            this.jComboBoxMissions.addItem(this.hashMapMission.get(m).getNom());
        }
        jComboBoxMissions.repaint();
    }

    /**
     * La fonction permet de récupérér toutes les missions
     * @return HashMap ayant pour clé : identifiant de la mission et pour valeur, la mission
     */
    public HashMap<String, Mission> initialiserHashMapMission() {
        HashMap<String, Mission> map = new HashMap<String, Mission>();
        for (Mission m : this.e.getListMis()) {
            map.put(m.getId(), m);
        }
        return map;
    }

    /**
     * La fonction permet de récupérér toutes les compétences
     * @return HashMap ayant pour clé : identifiant de la compétence et pour valeur, la compétence
     */
    public HashMap<String, Competence> initialiserHashMapCompetence() {
        HashMap<String, Competence> map = new HashMap<String, Competence>();
        for (Competence m : this.e.listComp) {
            map.put(m.getId(), m);
        }
        return map;
    }
    
    /**
     * La fonction permet de récupérér tous les employés
     * @return HashMap ayant pour clé : identifiant de l'employé et pour valeur, l'employé
     */
    public HashMap<String, Employe> initialiserHashMapEmploye() {
        HashMap<String, Employe> map = new HashMap<String, Employe>();
        for (Employe em : this.e.listEmp) {
            map.put(Integer.toString(em.getId()), em);
        }
        return map;
    }

    /**
     * 
     * @param nom
     * @return
     */
    public String nomMissionToId(String nom) {
        String id = "";
        for (String m : this.hashMapMission.keySet()) {
            if (nom == this.hashMapMission.get(m).getNom()) {
                id = m;
            }
        }
        return id;

    }

    private void initialiserTableCompetence(HashMap<Competence, Integer> recupComp) {
        model2.setRowCount(0);
        for (Competence c : recupComp.keySet()) {
            model2.insertRow(0, new Object[]{c.toString(), recupComp.get(c)});
        }
    }

    private void initialiserTableEmploye() throws FileNotFoundException, IOException {
        model1.setRowCount(0);
        String idDeLaMission = this.nomMissionToId((String) this.jComboBoxMissions.getSelectedItem());
        HashMap<Employe, Set<Competence>> map = this.employeCompetenceMission(this.e, this.hashMapMission.get(idDeLaMission));
        for (Employe emp : map.keySet()) {
            String lesCompetences = " ";
            for (Competence c : map.get(emp)) {
                lesCompetences += " " + c.getId() + " ";
            }
            model1.insertRow(0, new Object[]{emp.getNom() + " " + emp.getPrenom(), map.get(emp).size(), lesCompetences});
        }
    }
    
    /**
     *
     * @param nomEmp
     * @param prenomEmp
     * @return
     */
    public Employe employe (String nomEmp, String prenomEmp) {
        HashMap<String, Employe> hmEmploye = initialiserHashMapEmploye();
        Employe employe=new Employe();
        for (String id : hmEmploye.keySet()) {
            if (hmEmploye.get(id).getNom().equals(nomEmp) && hmEmploye.get(id).getPrenom().equals(prenomEmp))
                employe = hmEmploye.get(id);
        }
        return employe;
    } 

    /**
     *
     * @param e
     * @param m
     * @return
     */
    public HashMap <Employe,Set<Competence>> employeCompetenceMission (Entreprise e, Mission m) {
        HashMap <Employe,Set<Competence>> employeCompetenceMission = new HashMap <Employe,Set<Competence>>();
        ArrayList<Employe> listEmp = e.listEmp;
        ArrayList <String> listComp = new ArrayList <String>();
        
        for (Employe empl : listEmp) {
        listComp = empl.getListeComp();
            for (String idcompemp : listComp) {
                for (Competence c : m.mapC.keySet()) {
                    if (c.getId().equals(idcompemp)) {
                        if(!employeCompetenceMission.containsKey(empl)) {
                            Set <Competence> setcompetence = new HashSet<Competence>();
                            setcompetence.add(c);
                            employeCompetenceMission.put(empl, setcompetence);
                        }
                        else {
                            Set <Competence> scomp = employeCompetenceMission.get(empl);
                            scomp.add(c);
                            employeCompetenceMission.put(empl, scomp);
                        }
                    }
                }
            }
        }
        return employeCompetenceMission;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        PanelPrincipal = new javax.swing.JPanel();
        PanelAccueil = new javax.swing.JPanel();
        TAffEMploye = new javax.swing.JLabel();
        AffEmploye = new javax.swing.JButton();
        TAffComp = new javax.swing.JLabel();
        AffComp = new javax.swing.JButton();
        TAffMission = new javax.swing.JLabel();
        AffMission = new javax.swing.JButton();
        TCreerMission = new javax.swing.JLabel();
        CreerMission = new javax.swing.JButton();
        TFormation = new javax.swing.JLabel();
        FaireFormation = new javax.swing.JButton();
        TAffForm = new javax.swing.JLabel();
        AffForm = new javax.swing.JButton();
        TAffDate = new javax.swing.JLabel();
        ModifDate = new javax.swing.JButton();
        AffDate = new javax.swing.JLabel();
        TFormation1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        PanelFaireFormation = new javax.swing.JPanel();
        TEnvEmpForm = new javax.swing.JLabel();
        TIdForm = new javax.swing.JLabel();
        JTIdForm = new javax.swing.JTextField();
        TNomForm = new javax.swing.JLabel();
        JTNomForm = new javax.swing.JTextField();
        TDateForm = new javax.swing.JLabel();
        JDDateForm = new com.toedter.calendar.JDateChooser();
        SPanelFaireFormation = new javax.swing.JPanel();
        CBEmpDispo = new javax.swing.JComboBox<>();
        CBCompAcq = new javax.swing.JComboBox<>();
        TEmpDispo = new javax.swing.JLabel();
        TCompAcq = new javax.swing.JLabel();
        EnvFormation = new javax.swing.JButton();
        RetourPFormation = new javax.swing.JButton();
        PanelAffichage = new javax.swing.JPanel();
        ListeXXX = new javax.swing.JLabel();
        RetourPAffichage = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableAffichage = new javax.swing.JTable();
        Enregistrer = new javax.swing.JButton();
        CBIdMission = new javax.swing.JComboBox<>();
        TSelIdMission = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableEmployeMission = new javax.swing.JTable();
        PanelCreerMission = new javax.swing.JPanel();
        TCreerMission2 = new javax.swing.JLabel();
        RetourPCreerMission = new javax.swing.JButton();
        TIdMission = new javax.swing.JLabel();
        JTIdMission = new javax.swing.JTextField();
        TNomMission = new javax.swing.JLabel();
        JTNomMission = new javax.swing.JTextField();
        TDureeMission = new javax.swing.JLabel();
        JSpinDureeMission = new javax.swing.JSpinner();
        TBesoinEmp = new javax.swing.JLabel();
        JSBesoinEmp = new javax.swing.JSpinner();
        TDateDMission = new javax.swing.JLabel();
        JDDateDMission = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCreerMission = new javax.swing.JTable();
        TSelComp1 = new javax.swing.JLabel();
        TSelComp2 = new javax.swing.JLabel();
        EnrMission = new javax.swing.JButton();
        TitrePrincipal = new javax.swing.JLabel();
        PanelAffecterEmploye = new javax.swing.JPanel();
        RetourPAffectation = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxMissions = new javax.swing.JComboBox<>();
        buttonAfficherMissionCompetence = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableCompetences = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableEmployePropo = new javax.swing.JTable();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        TAffEMploye.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TAffEMploye.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TAffEMploye.setText("Afficher la liste des Employés");

        AffEmploye.setText("Liste Employé");
        AffEmploye.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AffEmployeMouseClicked(evt);
            }
        });
        AffEmploye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AffEmployeActionPerformed(evt);
            }
        });

        TAffComp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TAffComp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TAffComp.setText("Afficher la liste des Missions");

        AffComp.setText("Liste Competence");
        AffComp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AffCompMouseClicked(evt);
            }
        });

        TAffMission.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TAffMission.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TAffMission.setText("Afficher la liste des Compétences");

        AffMission.setText("Liste Mission");
        AffMission.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AffMissionMouseClicked(evt);
            }
        });
        AffMission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AffMissionActionPerformed(evt);
            }
        });

        TCreerMission.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TCreerMission.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TCreerMission.setText("Créer une Mission");

        CreerMission.setText("Créer");
        CreerMission.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CreerMissionMouseClicked(evt);
            }
        });
        CreerMission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreerMissionActionPerformed(evt);
            }
        });

        TFormation.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TFormation.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TFormation.setText("Affecter des employés à une mission");

        FaireFormation.setText("Formation");
        FaireFormation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FaireFormationMouseClicked(evt);
            }
        });
        FaireFormation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FaireFormationActionPerformed(evt);
            }
        });

        TAffForm.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TAffForm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TAffForm.setText("Afficher la liste des Formations");

        AffForm.setText("Liste Formation");
        AffForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AffFormMouseClicked(evt);
            }
        });

        TAffDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TAffDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TAffDate.setText("La date de l'entreprise");

        ModifDate.setText("Changer");
        ModifDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifDateActionPerformed(evt);
            }
        });

        AffDate.setText(" ");

        TFormation1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TFormation1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TFormation1.setText("Envoyer un employé en formation");

        jButton1.setText("Affecter");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelAccueilLayout = new javax.swing.GroupLayout(PanelAccueil);
        PanelAccueil.setLayout(PanelAccueilLayout);
        PanelAccueilLayout.setHorizontalGroup(
            PanelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TAffEMploye, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TAffMission, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TAffComp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelAccueilLayout.createSequentialGroup()
                .addGap(387, 387, 387)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PanelAccueilLayout.createSequentialGroup()
                .addGroup(PanelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAccueilLayout.createSequentialGroup()
                        .addGroup(PanelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelAccueilLayout.createSequentialGroup()
                                .addGap(415, 415, 415)
                                .addComponent(AffEmploye, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelAccueilLayout.createSequentialGroup()
                                .addGap(399, 399, 399)
                                .addComponent(AffComp, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelAccueilLayout.createSequentialGroup()
                                .addGap(419, 419, 419)
                                .addComponent(AffMission, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelAccueilLayout.createSequentialGroup()
                                .addGap(408, 408, 408)
                                .addComponent(AffForm, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelAccueilLayout.createSequentialGroup()
                                .addGap(460, 460, 460)
                                .addComponent(CreerMission, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelAccueilLayout.createSequentialGroup()
                                .addGap(397, 397, 397)
                                .addComponent(FaireFormation, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelAccueilLayout.createSequentialGroup()
                                .addGap(491, 491, 491)
                                .addComponent(ModifDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AffDate, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 345, Short.MAX_VALUE))
                    .addGroup(PanelAccueilLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TFormation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TAffForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TCreerMission, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TAffDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TFormation1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        PanelAccueilLayout.setVerticalGroup(
            PanelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAccueilLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(TAffEMploye)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AffEmploye)
                .addGap(32, 32, 32)
                .addComponent(TAffMission)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AffComp)
                .addGap(38, 38, 38)
                .addComponent(TAffComp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AffMission)
                .addGap(38, 38, 38)
                .addComponent(TAffForm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AffForm)
                .addGap(38, 38, 38)
                .addComponent(TCreerMission)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CreerMission)
                .addGap(47, 47, 47)
                .addComponent(TFormation1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FaireFormation)
                .addGap(41, 41, 41)
                .addComponent(TAffDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModifDate)
                    .addComponent(AffDate))
                .addGap(31, 31, 31)
                .addComponent(TFormation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        PanelFaireFormation.setVisible(false);
        PanelFaireFormation.setPreferredSize(new java.awt.Dimension(670, 835));

        TEnvEmpForm.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TEnvEmpForm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TEnvEmpForm.setText("Envoyer un employé en formation");

        TIdForm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TIdForm.setText("ID : ");

        JTIdForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTIdFormActionPerformed(evt);
            }
        });

        TNomForm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TNomForm.setText("Nom :");

        TDateForm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TDateForm.setText("Séléctionner la date à laquelle débutera la formation : ");

        JDDateForm.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                JDDateFormPropertyChange(evt);
            }
        });

        SPanelFaireFormation.setVisible(false);

        TEmpDispo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TEmpDispo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TEmpDispo.setText("Employés disponible");

        TCompAcq.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TCompAcq.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TCompAcq.setText("Compétences à acquérir");

        EnvFormation.setText("Envoyer en formation");
        EnvFormation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EnvFormationMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout SPanelFaireFormationLayout = new javax.swing.GroupLayout(SPanelFaireFormation);
        SPanelFaireFormation.setLayout(SPanelFaireFormationLayout);
        SPanelFaireFormationLayout.setHorizontalGroup(
            SPanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SPanelFaireFormationLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(SPanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TEmpDispo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBEmpDispo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(SPanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CBCompAcq, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TCompAcq, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(SPanelFaireFormationLayout.createSequentialGroup()
                .addGap(461, 461, 461)
                .addComponent(EnvFormation)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SPanelFaireFormationLayout.setVerticalGroup(
            SPanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SPanelFaireFormationLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(SPanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TEmpDispo)
                    .addComponent(TCompAcq))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SPanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CBEmpDispo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBCompAcq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(EnvFormation)
                .addContainerGap(380, Short.MAX_VALUE))
        );

        RetourPFormation.setText("Retour");
        RetourPFormation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RetourPFormationMouseClicked(evt);
            }
        });
        RetourPFormation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RetourPFormationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelFaireFormationLayout = new javax.swing.GroupLayout(PanelFaireFormation);
        PanelFaireFormation.setLayout(PanelFaireFormationLayout);
        PanelFaireFormationLayout.setHorizontalGroup(
            PanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFaireFormationLayout.createSequentialGroup()
                .addComponent(TEnvEmpForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(69, 69, 69))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelFaireFormationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RetourPFormation)
                .addContainerGap())
            .addGroup(PanelFaireFormationLayout.createSequentialGroup()
                .addComponent(SPanelFaireFormation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(PanelFaireFormationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelFaireFormationLayout.createSequentialGroup()
                        .addComponent(TDateForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDDateForm, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelFaireFormationLayout.createSequentialGroup()
                        .addComponent(TNomForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTNomForm))
                    .addGroup(PanelFaireFormationLayout.createSequentialGroup()
                        .addComponent(TIdForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTIdForm, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelFaireFormationLayout.setVerticalGroup(
            PanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFaireFormationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RetourPFormation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TEnvEmpForm)
                .addGap(60, 60, 60)
                .addGroup(PanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TIdForm)
                    .addComponent(JTIdForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TNomForm)
                    .addComponent(JTNomForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelFaireFormationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelFaireFormationLayout.createSequentialGroup()
                        .addComponent(TDateForm)
                        .addGap(58, 58, 58)
                        .addComponent(SPanelFaireFormation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelFaireFormationLayout.createSequentialGroup()
                        .addComponent(JDDateForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        PanelAffichage.setVisible(false);

        ListeXXX.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ListeXXX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ListeXXX.setText("Liste des ?????");

        RetourPAffichage.setText("Retour");
        RetourPAffichage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RetourPAffichageMouseClicked(evt);
            }
        });
        RetourPAffichage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RetourPAffichageActionPerformed(evt);
            }
        });

        jTableAffichage.setVisible(false);
        jTableAffichage.setModel(new javax.swing.table.DefaultTableModel(
            new Object [0][0],
            new String [0]
        ));
        jScrollPane3.setViewportView(jTableAffichage);

        Enregistrer.setText("Enregistrer");
        Enregistrer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EnregistrerMouseClicked(evt);
            }
        });

        CBIdMission.setVisible(false);
        CBIdMission.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CBIdMissionItemStateChanged(evt);
            }
        });

        TSelIdMission.setVisible(false);
        TSelIdMission.setText("Mission : ");

        jTableEmployeMission.setModel(new javax.swing.table.DefaultTableModel(
            new Object [0][0],
            new String [0]
        ));
        jScrollPane2.setViewportView(jTableEmployeMission);

        javax.swing.GroupLayout PanelAffichageLayout = new javax.swing.GroupLayout(PanelAffichage);
        PanelAffichage.setLayout(PanelAffichageLayout);
        PanelAffichageLayout.setHorizontalGroup(
            PanelAffichageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ListeXXX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAffichageLayout.createSequentialGroup()
                .addGroup(PanelAffichageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAffichageLayout.createSequentialGroup()
                        .addContainerGap(996, Short.MAX_VALUE)
                        .addComponent(RetourPAffichage))
                    .addGroup(PanelAffichageLayout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addGroup(PanelAffichageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelAffichageLayout.createSequentialGroup()
                                .addComponent(TSelIdMission)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CBIdMission, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(185, 185, 185)
                                .addComponent(Enregistrer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 488, Short.MAX_VALUE))
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        PanelAffichageLayout.setVerticalGroup(
            PanelAffichageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAffichageLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(RetourPAffichage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ListeXXX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelAffichageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Enregistrer)
                    .addComponent(CBIdMission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TSelIdMission))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelAffichage.setVisible(false);
        PanelCreerMission.setVisible(false);

        TCreerMission2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TCreerMission2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TCreerMission2.setText("Créer une Mission");

        RetourPCreerMission.setText("Retour");
        RetourPCreerMission.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RetourPCreerMissionMouseClicked(evt);
            }
        });
        RetourPCreerMission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RetourPCreerMissionActionPerformed(evt);
            }
        });

        TIdMission.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TIdMission.setText("Id de la mission :");

        TNomMission.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TNomMission.setText("Nom de la mission :");

        TDureeMission.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TDureeMission.setText("Duree de la mission (en jours) :");

        TBesoinEmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TBesoinEmp.setText("Besoin en employé :");

        TDateDMission.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TDateDMission.setText("Date de début de la mission : ");

        jTableCreerMission.setVisible(false);
        jTableCreerMission.setModel(new javax.swing.table.DefaultTableModel(
            new Object [0][0],
            new String [0]
        ));
        jScrollPane1.setViewportView(jTableCreerMission);

        TSelComp1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TSelComp1.setText("Selectionner pour chaque compétence, le nombre d'employé necessaire : ");

        TSelComp2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TSelComp2.setText("(Laisser 0 si la mission n'a pas besoin de la compétence)");
        TSelComp2.setAlignmentY(2.0F);

        EnrMission.setText("Créer");
        EnrMission.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EnrMissionMouseClicked(evt);
            }
        });
        EnrMission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnrMissionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelCreerMissionLayout = new javax.swing.GroupLayout(PanelCreerMission);
        PanelCreerMission.setLayout(PanelCreerMissionLayout);
        PanelCreerMissionLayout.setHorizontalGroup(
            PanelCreerMissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TCreerMission2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelCreerMissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                        .addGap(0, 956, Short.MAX_VALUE)
                        .addComponent(RetourPCreerMission)
                        .addGap(10, 10, 10))
                    .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                        .addGroup(PanelCreerMissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelCreerMissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                                    .addComponent(TIdMission)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(JTIdMission, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                                    .addComponent(TNomMission)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(JTNomMission, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                                    .addComponent(TDureeMission)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(JSpinDureeMission)))
                            .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                                .addComponent(TBesoinEmp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JSBesoinEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(TSelComp1)
                            .addComponent(TSelComp2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                        .addComponent(TDateDMission)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDDateDMission, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                .addGap(491, 491, 491)
                .addComponent(EnrMission)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelCreerMissionLayout.setVerticalGroup(
            PanelCreerMissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(RetourPCreerMission)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TCreerMission2)
                .addGap(46, 46, 46)
                .addGroup(PanelCreerMissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TIdMission)
                    .addComponent(JTIdMission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelCreerMissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TNomMission)
                    .addComponent(JTNomMission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelCreerMissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TDureeMission)
                    .addComponent(JSpinDureeMission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelCreerMissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TBesoinEmp)
                    .addComponent(JSBesoinEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PanelCreerMissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(TDateDMission))
                    .addGroup(PanelCreerMissionLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JDDateDMission, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(TSelComp1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TSelComp2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(EnrMission)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        TitrePrincipal.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        TitrePrincipal.setText("Gestion de l'entreprise");

        PanelAffecterEmploye.setVisible(false);

        RetourPAffectation.setText("Retour");
        RetourPAffectation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RetourPAffectationMouseClicked(evt);
            }
        });
        RetourPAffectation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RetourPAffectationActionPerformed(evt);
            }
        });

        jLabel1.setText("Choisir une mission");

        jComboBoxMissions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMissionsActionPerformed(evt);
            }
        });

        buttonAfficherMissionCompetence.setText("Afficher");
        buttonAfficherMissionCompetence.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonAfficherMissionCompetenceMouseClicked(evt);
            }
        });
        buttonAfficherMissionCompetence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAfficherMissionCompetenceActionPerformed(evt);
            }
        });

        jTableCompetences.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Compétence", "Nombre de personnes"
            }
        ));
        jScrollPane4.setViewportView(jTableCompetences);

        jLabel2.setText("Sélectionner un employé pour l'ajouter à votre mission");

        jTableEmployePropo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Employé", "Nombre de compétences mission", "Compétences"
            }
        ));
        jTableEmployePropo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEmployePropoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableEmployePropo);

        javax.swing.GroupLayout PanelAffecterEmployeLayout = new javax.swing.GroupLayout(PanelAffecterEmploye);
        PanelAffecterEmploye.setLayout(PanelAffecterEmployeLayout);
        PanelAffecterEmployeLayout.setHorizontalGroup(
            PanelAffecterEmployeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAffecterEmployeLayout.createSequentialGroup()
                .addGroup(PanelAffecterEmployeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAffecterEmployeLayout.createSequentialGroup()
                        .addContainerGap(976, Short.MAX_VALUE)
                        .addComponent(RetourPAffectation))
                    .addGroup(PanelAffecterEmployeLayout.createSequentialGroup()
                        .addGroup(PanelAffecterEmployeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelAffecterEmployeLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel1)
                                .addGap(77, 77, 77)
                                .addComponent(jComboBoxMissions, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(130, 130, 130)
                                .addComponent(buttonAfficherMissionCompetence))
                            .addGroup(PanelAffecterEmployeLayout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 839, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelAffecterEmployeLayout.createSequentialGroup()
                                .addGap(311, 311, 311)
                                .addComponent(jLabel2))
                            .addGroup(PanelAffecterEmployeLayout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelAffecterEmployeLayout.setVerticalGroup(
            PanelAffecterEmployeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAffecterEmployeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RetourPAffectation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelAffecterEmployeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(PanelAffecterEmployeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxMissions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonAfficherMissionCompetence)))
                .addGap(51, 51, 51)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jLabel2)
                .addGap(68, 68, 68)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(226, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelPrincipalLayout = new javax.swing.GroupLayout(PanelPrincipal);
        PanelPrincipal.setLayout(PanelPrincipalLayout);
        PanelPrincipalLayout.setHorizontalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelAccueil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addGap(353, 353, 353)
                .addComponent(TitrePrincipal)
                .addContainerGap(353, Short.MAX_VALUE))
            .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PanelAffichage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(PanelCreerMission, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PanelFaireFormation, javax.swing.GroupLayout.DEFAULT_SIZE, 1083, Short.MAX_VALUE))
            .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(PanelAffecterEmploye, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        PanelPrincipalLayout.setVerticalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(TitrePrincipal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelAccueil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPrincipalLayout.createSequentialGroup()
                    .addGap(0, 14, Short.MAX_VALUE)
                    .addComponent(PanelAffichage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPrincipalLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addComponent(PanelCreerMission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(164, Short.MAX_VALUE)))
            .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPrincipalLayout.createSequentialGroup()
                    .addGap(0, 60, Short.MAX_VALUE)
                    .addComponent(PanelFaireFormation, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelPrincipalLayout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addComponent(PanelAffecterEmploye, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(156, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AffMissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AffMissionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AffMissionActionPerformed

    /**
     * Defini la structure de la jTable pour afficher les employe
     * @param evt 
     */
    private void AffEmployeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AffEmployeMouseClicked
        // TODO add your handling code here:
        int i = 0;

        jTableAffichage.setModel(new javax.swing.table.DefaultTableModel(
            new Object [ent.getListEmp().size()][4],
            new String [] {
                "Nom", "Prenom", "DateE", "Competence"
            }
        ));

        for(Employe e : ent.getListEmp()){
            jTableAffichage.setValueAt(e.getNom(), i, 0);
            jTableAffichage.setValueAt(e.getPrenom(), i, 1);
            jTableAffichage.setValueAt(e.getDateE(), i, 2);
            jTableAffichage.setValueAt(e.getListeComp(), i, 3);
            i++;
        }
        PanelAccueil.setVisible(false);
        PanelAffichage.setVisible(true);
        ListeXXX.setText("Liste des Employés");

        jTableAffichage.setVisible(true);
            
    }//GEN-LAST:event_AffEmployeMouseClicked

    /**
     * Defini la structure de la jTable pour afficher les competence
     * @param evt 
     */
    private void AffCompMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AffCompMouseClicked
        // TODO add your handling code here:
        int i = 0;

        jTableAffichage.setModel(new javax.swing.table.DefaultTableModel(
            new Object [ent.getListComp().size()][2],
            new String [] {
                "Id", "Fr", "Eng"
            }
        ));

        for(Competence c : ent.getListComp()){
            jTableAffichage.setValueAt(c.getId(), i, 0);
            jTableAffichage.setValueAt(c.getLibFr(), i, 1);
            jTableAffichage.setValueAt(c.getLibEng(), i, 2);
            i++;
        }
        PanelAccueil.setVisible(false);
        PanelAffichage.setVisible(true);
        ListeXXX.setText("Liste des Competences");
        jTableAffichage.setVisible(true);
    }//GEN-LAST:event_AffCompMouseClicked

    private void AffEmployeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AffEmployeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AffEmployeActionPerformed

    private void RetourPAffichageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RetourPAffichageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RetourPAffichageActionPerformed

    /**
     * Defini la structure de la jTable pour afficher les mission
     * @param evt 
     */
    private void AffMissionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AffMissionMouseClicked
        // TODO add your handling code here:               
        int i = 0;

        jTableAffichage.setModel(new javax.swing.table.DefaultTableModel(
            new Object [ent.getListMis().size()][7],
            new String [] {
                "Id", "Nom", "Type", "Date de début", "Duree (en jours)", "Employes affectés", "Competence : employe necessaire"
            }
        ));

        for(Mission m : ent.getListMis()){
            jTableAffichage.setValueAt(m.getId(), i, 0);
            jTableAffichage.setValueAt(m.getNom(), i, 1);
            jTableAffichage.setValueAt(m.getType(), i, 2);
            jTableAffichage.setValueAt(m.getDateD(), i, 3);
            jTableAffichage.setValueAt(m.getDuree(), i, 4);
            jTableAffichage.setValueAt(m.getNbTotalEmp(), i, 5);
            String mapC = "";
            for(Map.Entry<Competence, Integer> ci : m.getMapC().entrySet()){
                mapC += "{" + ci.getKey().getId() + "} : " + ci.getValue() + ", ";
            }
            jTableAffichage.setValueAt(mapC, i, 6);
        }
        
        for(Mission m : ent.listMis){
            CBIdMission.addItem(m.getId());
        }

        PanelAccueil.setVisible(false);
        PanelAffichage.setVisible(true);
        Enregistrer.setVisible(false);
        TSelIdMission.setVisible(true);
        CBIdMission.setVisible(true);
        ListeXXX.setText("Liste des Missions");

        jTableAffichage.setVisible(true);
    }//GEN-LAST:event_AffMissionMouseClicked

    private void RetourPAffichageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RetourPAffichageMouseClicked
        // TODO add your handling code here:
        PanelAffichage.setVisible(false);
        Enregistrer.setVisible(true);
        TSelIdMission.setVisible(false);
        CBIdMission.setVisible(false);
        PanelAccueil.setVisible(true);
    }//GEN-LAST:event_RetourPAffichageMouseClicked

    /**
     * Enregistre les modification faite dans la jTable des employe ou des competences
     * @param evt 
     */
    private void EnregistrerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EnregistrerMouseClicked
        try {
            // TODO add your handling code here:
            switch(ListeXXX.getText()){
                case "Liste des Employés":
                    ArrayList<Employe> listE = new ArrayList<Employe>();
                    ArrayList<String> listCE = new ArrayList<String>();

                    for(int i=0; i<jTableAffichage.getRowCount(); i++){
                        Employe e = new Employe(i+1, (String)jTableAffichage.getValueAt(i, 1), (String)jTableAffichage.getValueAt(i, 0), (String)jTableAffichage.getValueAt(i, 2));
                        listCE = (ArrayList<String>)jTableAffichage.getValueAt(i, 3);
                        e.setListeComp(listCE);
                        listE.add(e);
                    }
                    ent.sauvegarderEmploye(listE);
                    break;
                case "Liste des Competences":
                    ArrayList<Competence> listC = new ArrayList<Competence>();
                    
                    for(int i=0; i<jTableAffichage.getRowCount(); i++){
                        Competence c = new Competence((String)jTableAffichage.getValueAt(i, 0), (String)jTableAffichage.getValueAt(i, 2), (String)jTableAffichage.getValueAt(i, 1));
                        listC.add(c);
                    }
                    ent.sauvegarderCompetence(listC);
                    break;
                default:
                    break;
            }
            PanelAffichage.setVisible(false);
            PanelAccueil.setVisible(true);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EnregistrerMouseClicked

    private void CreerMissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreerMissionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CreerMissionActionPerformed

    private void RetourPCreerMissionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RetourPCreerMissionMouseClicked
        // TODO add your handling code here:
        PanelCreerMission.setVisible(false);
        PanelAccueil.setVisible(true);
    }//GEN-LAST:event_RetourPCreerMissionMouseClicked

    private void RetourPCreerMissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RetourPCreerMissionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RetourPCreerMissionActionPerformed

    /**
     * Parametre et affiche la fenetre pour créer une mission
     * @param evt 
     */
    private void CreerMissionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CreerMissionMouseClicked
        // TODO add your handling code here:      
        int i = 0;

        jTableCreerMission.setModel(new javax.swing.table.DefaultTableModel(
            new Object [ent.getListComp().size()][2],
            new String [] {
                "Competence", "Nombre d'employé",
            }
        ));

        for(Competence c : ent.getListComp()){
            jTableCreerMission.setValueAt(c, i, 0);
            jTableCreerMission.setValueAt(0, i, 1);
            i++;
        }
        PanelAccueil.setVisible(false);
        PanelCreerMission.setVisible(true);

        jTableCreerMission.setVisible(true);
    }//GEN-LAST:event_CreerMissionMouseClicked

    private void EnrMissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnrMissionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EnrMissionActionPerformed

    /**
     * Enregistre une mission avec ls informations rentrées
     * @param evt 
     */
    private void EnrMissionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EnrMissionMouseClicked
        try {
            // TODO add your handling code here:
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date d = sdf.parse(JDDateDMission.getDate().toString());
            HashMap<Competence, Integer> mapC = new HashMap<Competence, Integer>();
            for(int i=0; i<jTableCreerMission.getRowCount(); i++){
                Competence c = (Competence)jTableCreerMission.getValueAt(i, 0);
                if((jTableCreerMission.getValueAt(i, 1)) instanceof String){
                    if(Integer.parseInt((String)jTableCreerMission.getValueAt(i, 1)) > 0){
                        mapC.put(c, Integer.parseInt((String)jTableCreerMission.getValueAt(i, 1)));
                    }
                }
                else {
                    if((Integer)jTableCreerMission.getValueAt(i, 1) > 0){
                        mapC.put(c, (Integer)jTableCreerMission.getValueAt(i, 1));                        
                    }
                }
            }
            Mission nouvMission = new Mission(JTIdMission.getText(), JTNomMission.getText(), d, (int)JSpinDureeMission.getValue(), (int)JSBesoinEmp.getValue());
            nouvMission.setMapC(mapC);
            ent.ajouterMission(nouvMission);
            
            PanelCreerMission.setVisible(false);
            PanelAccueil.setVisible(true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*catch(Exception e){
        System.out.println(e);
        }*/  /*catch(Exception e){
            System.out.println(e);
        }*/
    }//GEN-LAST:event_EnrMissionMouseClicked

    private void FaireFormationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FaireFormationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaireFormationActionPerformed

    private void FaireFormationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FaireFormationMouseClicked
        // TODO add your handling code here:
        PanelAccueil.setVisible(false);
        PanelFaireFormation.setVisible(true);
    }//GEN-LAST:event_FaireFormationMouseClicked

    private void RetourPFormationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RetourPFormationMouseClicked
        // TODO add your handling code here:
        JTIdForm.setText("");
        JTNomForm.setText("");
        JDDateForm.setDate(null);
        SPanelFaireFormation.setVisible(false);
        PanelFaireFormation.setVisible(false);
        PanelAccueil.setVisible(true);
    }//GEN-LAST:event_RetourPFormationMouseClicked

    private void RetourPFormationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RetourPFormationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RetourPFormationActionPerformed

    /**
     * Parametre et affiche les composatns de la fenetre pour créer une formation quand la date de début de la foramtion est renseignée
     * @param evt 
     */
    private void JDDateFormPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_JDDateFormPropertyChange
        // TODO add your handling code here:
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        if(evt.getPropertyName().equals("date") && evt.getOldValue()==null && evt.getNewValue()!=null){
            ArrayList<Employe> listTmpEmp = ent.getListEmp();
            ArrayList<Mission> listTmpMis = ent.getListMis();
            ArrayList<Formation> listTmpForm = ent.getListForm();
            for (Mission m : listTmpMis){
                Date dateFinMis = m.getDateD();
                dateFinMis.setTime(m.getDateD().getTime() + (m.getDuree()*86400000));
                try {
                    if(sdf.parse(JDDateForm.getDate().toString()).compareTo(m.getDateD()) < 0 && sdf.parse(JDDateForm.getDate().toString()).compareTo(dateFinMis) > 0){
                        for(Employe e : m.getEmpMission()){
                            if(listTmpEmp.contains(e)){
                                listTmpEmp.remove(e);
                            }
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Formation f : listTmpForm){
                Date dateFinForm = new Date(f.getDateD().getTime());
                dateFinForm.setTime(f.getDateD().getTime() + (f.getDuree()*86400000));
                try {
                    if(sdf.parse(JDDateForm.getDate().toString()).compareTo(f.getDateD()) > 0 && sdf.parse(JDDateForm.getDate().toString()).compareTo(dateFinForm) < 0){
                        if(listTmpEmp.contains(f.getEmp())){
                            listTmpEmp.remove(f.getEmp());
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for(Employe e : listTmpEmp){
                CBEmpDispo.addItem(e.getId() + " " + e.getNom() + " " + e.getPrenom());
            }
            for(Competence c : ent.getListComp()){
                CBCompAcq.addItem(c.getId() + " " + c.getLibFr());
            }
            SPanelFaireFormation.setVisible(true);
        }
    }//GEN-LAST:event_JDDateFormPropertyChange

    /**
     * Permet d'envoyé un employé en formation avec les informations renseignée (la durée d'un formation est de 10jours)
     * @param evt 
     */
    private void EnvFormationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EnvFormationMouseClicked
        // TODO add your handling code here:
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Competence cForm = new Competence();
            Employe[] eForm = new Employe[1];
            Formation f = new Formation(JTIdForm.getText(), JTNomForm.getText(), sdf.parse(JDDateForm.getDate().toString()), 10, 1);
            
            for (Employe e : ent.getListEmp()) {
                if(e.getId() == Integer.parseInt(((String)CBEmpDispo.getSelectedItem()).split(" ")[0]) ){
                    eForm[0] = e;
                }
            }
            
            for (Competence c : ent.getListComp()) {
                if(c.getId().equals(((String)CBCompAcq.getSelectedItem()).split(" ")[0])){
                    cForm = c;
                }
            }
            
            f.setMapE(cForm, eForm);
            ent.ajouterFormation(f);
            
            JTIdForm.setText("");
            JTNomForm.setText("");
            JDDateForm.setDate(null);
            SPanelFaireFormation.setVisible(false);
            PanelFaireFormation.setVisible(false);
            PanelAccueil.setVisible(true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EnvFormationMouseClicked

    private void JTIdFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTIdFormActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTIdFormActionPerformed

    /**
     * Defini la structure de la jTable pour afficher les formations
     * @param evt 
     */
    private void AffFormMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AffFormMouseClicked
        // TODO add your handling code here:
        int i = 0;

        jTableAffichage.setModel(new javax.swing.table.DefaultTableModel(
            new Object [ent.getListForm().size()][7],
            new String [] {
                "Id", "Nom", "Type", "Date de début", "Duree (en jours)", "Employe", "Competence a acquérir"
            }
        ));

        for(Formation f : ent.getListForm()){
            jTableAffichage.setValueAt(f.getId(), i, 0);
            jTableAffichage.setValueAt(f.getNom(), i, 1);
            jTableAffichage.setValueAt(f.getType(), i, 2);
            jTableAffichage.setValueAt(f.getDateD(), i, 3);
            jTableAffichage.setValueAt(f.getDuree(), i, 4);
            jTableAffichage.setValueAt(f.getEmp().getId() + " " + f.getEmp().getNom() + " " + f.getEmp().getPrenom(), i, 5);
            jTableAffichage.setValueAt(f.getComp().getId(), i, 6);
        }

        PanelAccueil.setVisible(false);
        PanelAffichage.setVisible(true);
        Enregistrer.setVisible(false);
        ListeXXX.setText("Liste des Formations");

        jTableAffichage.setVisible(true);
    }//GEN-LAST:event_AffFormMouseClicked

    private void ModifDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifDateActionPerformed
        // TODO add your handling code here:
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
                            //Creation du calendrier
                            JCalendar c = new JCalendar();

                            JDialog d = new JDialog(); // type de fenetre
                            d.setTitle("Choisir une date");
                            d.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                            d.add(c);
                            d.pack();
                            d.setVisible(true);

                            Date date = c.getCalendar().getTime(); 
                            // Recuperation de la Date sous le bon format
                            //Affichage :
                            //Le pays pour avoir la bonne date
                            Locale l = Locale.getDefault();
                            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, l);
                            DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
                            AffDate.setText(": "+mediumDateFormat.format(date));

                            try {
                                ent.mAJDate(ent,date);
                            } catch (FileNotFoundException ex) {
                            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		});        
        
    }//GEN-LAST:event_ModifDateActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        PanelAccueil.setVisible(false);
        PanelAffecterEmploye.setVisible(true);
        buttonAfficherMissionCompetence.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked

    private void RetourPAffectationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RetourPAffectationMouseClicked
        try {
            // TODO add your handling code here:
            ent.sauvegarderEmployeMission(ent.getListMis());
        } catch (IOException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
        PanelAffecterEmploye.setVisible(false);
        PanelAccueil.setVisible(true);
    }//GEN-LAST:event_RetourPAffectationMouseClicked

    private void RetourPAffectationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RetourPAffectationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RetourPAffectationActionPerformed

    /**
     * Quand on selectionne un item dans la CheckBox qui contient les id des missions, cela affiche dans la jTable les competence de la mission et les employé qui sont dessus 
     * @param evt 
     */
    private void CBIdMissionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CBIdMissionItemStateChanged
        // TODO add your handling code here:
        int j=0;
        jTableEmployeMission.setModel(new javax.swing.table.DefaultTableModel(
            new Object [ent.getListEmp().size()][2],
            new String [] {
                "Competence", "Employe"
            }
        ));
        
        for(Mission m : ent.getListMis()){
            if(m.getId().equals(CBIdMission.getSelectedItem())){
                for(Map.Entry<Competence, Employe[]> ce : m.getMapE().entrySet()){
                    for(int i=0; i<ce.getValue().length; i++){
                        jTableEmployeMission.setValueAt(ce.getKey(), j, 0);
                        jTableEmployeMission.setValueAt(ce.getValue()[i], j, 1);
                        j++;
                    }
                }
            }
        }       
    }//GEN-LAST:event_CBIdMissionItemStateChanged

    private void buttonAfficherMissionCompetenceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonAfficherMissionCompetenceMouseClicked
        String nomMission;
        nomMission = (String) jComboBoxMissions.getSelectedItem();
        String id = this.nomMissionToId(nomMission);

        try {
            Mission mis = this.hashMapMission.get(id);
            HashMap <Competence,Integer> compMis = mis.mapC;
            this.initialiserTableCompetence(compMis);
            HashMap <Employe,Set<Competence>> map = this.employeCompetenceMission(e, mis);
            this.initialiserTableEmploye();

        } catch (IOException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonAfficherMissionCompetenceMouseClicked

    private void buttonAfficherMissionCompetenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAfficherMissionCompetenceActionPerformed

    }//GEN-LAST:event_buttonAfficherMissionCompetenceActionPerformed

    private void jComboBoxMissionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMissionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMissionsActionPerformed

    private void jTableEmployePropoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEmployePropoMouseClicked
        String InfoEmploye = jTableEmployePropo.getValueAt(jTableEmployePropo.getSelectedRow(), 0).toString();
        String [] tabInfoEmp = InfoEmploye.split(" ");
        String nomEmp = tabInfoEmp[0];
        String prenomEmp = tabInfoEmp[1];
        Employe emp= employe(nomEmp,prenomEmp);

        String nomMission = (String) jComboBoxMissions.getSelectedItem();
        String id = this.nomMissionToId(nomMission);
        Mission mis = this.hashMapMission.get(id);

        String compEmploye = jTableEmployePropo.getValueAt(jTableEmployePropo.getSelectedRow(), 2).toString();
        HashMap <Employe,Set<Competence>> emComMis = employeCompetenceMission (this.e, mis);
        Set<Competence> compRech = emComMis.get(emp);
        
        Employe [] tabEmp = new Employe[1];

        for(Mission m : ent.getListMis()){
            if (m.getId().equals(mis.getId())){
                for(Competence c : compRech){
                    m.ajouterCompetenceEmploye(c, emp);
                }
            }
        }

    }//GEN-LAST:event_jTableEmployePropoMouseClicked

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Accueil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Accueil a;
                try {
                    a = new Accueil();
                    a.setVisible(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AffComp;
    private javax.swing.JLabel AffDate;
    private javax.swing.JButton AffEmploye;
    private javax.swing.JButton AffForm;
    private javax.swing.JButton AffMission;
    private javax.swing.JComboBox<String> CBCompAcq;
    private javax.swing.JComboBox<String> CBEmpDispo;
    private javax.swing.JComboBox<String> CBIdMission;
    private javax.swing.JButton CreerMission;
    private javax.swing.JButton EnrMission;
    private javax.swing.JButton Enregistrer;
    private javax.swing.JButton EnvFormation;
    private javax.swing.JButton FaireFormation;
    private com.toedter.calendar.JDateChooser JDDateDMission;
    private com.toedter.calendar.JDateChooser JDDateForm;
    private javax.swing.JSpinner JSBesoinEmp;
    private javax.swing.JSpinner JSpinDureeMission;
    private javax.swing.JTextField JTIdForm;
    private javax.swing.JTextField JTIdMission;
    private javax.swing.JTextField JTNomForm;
    private javax.swing.JTextField JTNomMission;
    private javax.swing.JLabel ListeXXX;
    private javax.swing.JButton ModifDate;
    private javax.swing.JPanel PanelAccueil;
    private javax.swing.JPanel PanelAffecterEmploye;
    private javax.swing.JPanel PanelAffichage;
    private javax.swing.JPanel PanelCreerMission;
    private javax.swing.JPanel PanelFaireFormation;
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JButton RetourPAffectation;
    private javax.swing.JButton RetourPAffichage;
    private javax.swing.JButton RetourPCreerMission;
    private javax.swing.JButton RetourPFormation;
    private javax.swing.JPanel SPanelFaireFormation;
    private javax.swing.JLabel TAffComp;
    private javax.swing.JLabel TAffDate;
    private javax.swing.JLabel TAffEMploye;
    private javax.swing.JLabel TAffForm;
    private javax.swing.JLabel TAffMission;
    private javax.swing.JLabel TBesoinEmp;
    private javax.swing.JLabel TCompAcq;
    private javax.swing.JLabel TCreerMission;
    private javax.swing.JLabel TCreerMission2;
    private javax.swing.JLabel TDateDMission;
    private javax.swing.JLabel TDateForm;
    private javax.swing.JLabel TDureeMission;
    private javax.swing.JLabel TEmpDispo;
    private javax.swing.JLabel TEnvEmpForm;
    private javax.swing.JLabel TFormation;
    private javax.swing.JLabel TFormation1;
    private javax.swing.JLabel TIdForm;
    private javax.swing.JLabel TIdMission;
    private javax.swing.JLabel TNomForm;
    private javax.swing.JLabel TNomMission;
    private javax.swing.JLabel TSelComp1;
    private javax.swing.JLabel TSelComp2;
    private javax.swing.JLabel TSelIdMission;
    private javax.swing.JLabel TitrePrincipal;
    private javax.swing.JButton buttonAfficherMissionCompetence;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxMissions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableAffichage;
    private javax.swing.JTable jTableCompetences;
    private javax.swing.JTable jTableCreerMission;
    private javax.swing.JTable jTableEmployeMission;
    private javax.swing.JTable jTableEmployePropo;
    // End of variables declaration//GEN-END:variables
}
