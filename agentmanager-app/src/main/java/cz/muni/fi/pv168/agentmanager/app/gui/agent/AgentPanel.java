/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168.agentmanager.app.gui.agent;

import cz.muni.fi.pv168.gmiterkosys.Agent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Jaromir Sys
 */
public class AgentPanel extends javax.swing.JPanel {

    public void setAgent(Agent agent) {
        this.nameTextField.setText(agent.getName());
        this.levelSpinner.setValue(agent.getLevel());
        this.bornSpinner.setValue(Date.from(agent.getBorn().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (agent.getDied() != null) {
            this.diedCheckbox.setSelected(true);
            this.diedSpinner.setValue(Date.from(agent.getDied().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
    }

    /**
     * Creates new form AgentPanel
     */
    public AgentPanel() {
        initComponents();

//         DateFormatSymbols dfs = DateFormatSymbols.getInstance(Locale.forLanguageTag("cs_CZ"));
//         System.err.println(dfs.g);
//         ((DateEditor)this.diedSpinner.getEditor()).getFormat().applyPattern("yyyy-MM-dd HH:mm:ss");
    }

    public String getAgentName() {
        return nameTextField.getText();
    }

    public LocalDate getAgentBorn() {
        Date date = (Date) bornSpinner.getValue();
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDate getAgentDied() {
        if (diedCheckbox.isSelected()) {
            Date date = (Date) diedSpinner.getValue();
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
        } else {
            return null;
        }
    }

    public int getAgentLevel() {
        return (int) levelSpinner.getValue();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        namePanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        bornPanel = new javax.swing.JPanel();
        bornLabel = new javax.swing.JLabel();
        bornSpinner = new javax.swing.JSpinner();
        diedPanel = new javax.swing.JPanel();
        diedCheckbox = new javax.swing.JCheckBox();
        diedSpinner = new javax.swing.JSpinner();
        levelPanel = new javax.swing.JPanel();
        levelLabel = new javax.swing.JLabel();
        levelSpinner = new javax.swing.JSpinner();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        namePanel.setLayout(new java.awt.GridLayout(1, 0));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/pv168/agentmanager/app/Texts"); // NOI18N
        nameLabel.setText(bundle.getString("agent.name")); // NOI18N
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);

        add(namePanel);

        bornPanel.setLayout(new java.awt.GridLayout(1, 0));

        bornLabel.setText(bundle.getString("agent.born")); // NOI18N
        bornPanel.add(bornLabel);

        bornSpinner.setModel(new javax.swing.SpinnerDateModel());
        bornSpinner.setToolTipText("");
        //((DateEditor)bornSpinner.getEditor()).getFormat().applyPattern("yyyy-MM-dd");
        bornPanel.add(bornSpinner);

        add(bornPanel);

        diedPanel.setLayout(new java.awt.GridLayout(1, 0));

        diedCheckbox.setText(bundle.getString("agent.died")); // NOI18N
        diedCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                diedCheckboxItemStateChanged(evt);
            }
        });
        diedPanel.add(diedCheckbox);

        diedSpinner.setModel(new javax.swing.SpinnerDateModel());
        diedSpinner.setEnabled(false);
        //((DateEditor)diedSpinner.getEditor()).getFormat().applyPattern("yyyy-MM-dd");
        diedPanel.add(diedSpinner);

        add(diedPanel);

        levelPanel.setLayout(new java.awt.GridLayout(1, 0));

        levelLabel.setText(bundle.getString("agent.level")); // NOI18N
        levelPanel.add(levelLabel);

        levelSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));
        levelPanel.add(levelSpinner);

        add(levelPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void diedCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_diedCheckboxItemStateChanged
        if (diedCheckbox.isSelected()) {
            diedSpinner.setEnabled(true);
        } else {
            diedSpinner.setEnabled(false);
        }

    }//GEN-LAST:event_diedCheckboxItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bornLabel;
    private javax.swing.JPanel bornPanel;
    private javax.swing.JSpinner bornSpinner;
    private javax.swing.JCheckBox diedCheckbox;
    private javax.swing.JPanel diedPanel;
    private javax.swing.JSpinner diedSpinner;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JPanel levelPanel;
    private javax.swing.JSpinner levelSpinner;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel namePanel;
    private javax.swing.JTextField nameTextField;
    // End of variables declaration//GEN-END:variables
}
