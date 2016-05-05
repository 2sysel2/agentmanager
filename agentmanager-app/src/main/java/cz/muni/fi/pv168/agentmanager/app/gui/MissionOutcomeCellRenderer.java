package cz.muni.fi.pv168.agentmanager.app.gui;

import cz.muni.fi.pv168.gmiterkosys.Outcome;
import java.util.ResourceBundle;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dominik Gmiterko
 */
public class MissionOutcomeCellRenderer extends DefaultTableCellRenderer{

    private ResourceBundle texty;

    public MissionOutcomeCellRenderer(ResourceBundle texty) {
        this.texty = texty;
    }
    
    @Override
    protected void setValue(Object value) {
        if(value == null) {
            setText("");
            return;
        }
        setText( texty.getString("outcome."+((Outcome) value).name().toLowerCase()) );
    }

    

}
