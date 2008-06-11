package org.nyet.ecuxplot;

import java.util.prefs.Preferences;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FilterEditor extends PreferencesEditor {
    private Filter filter;

    private JTextField gear;
    private JTextField minRPM;
    private JTextField maxRPM;
    private JTextField minPedal;
    private JTextField minThrottle;
    private JTextField minPoints;

    protected void Process(ActionEvent event) {
	if(this.filter==null) return;
	this.filter.gear(Integer.valueOf(this.gear.getText()));
	this.filter.minRPM(Integer.valueOf(this.minRPM.getText()));
	this.filter.maxRPM(Integer.valueOf(this.maxRPM.getText()));
	this.filter.minPedal(Integer.valueOf(this.minPedal.getText()));
	this.filter.minThrottle(Integer.valueOf(this.minThrottle.getText()));
	this.filter.minPoints(Integer.valueOf(this.minPoints.getText()));
	super.Process(event);
    }

    public FilterEditor (Preferences prefs) {
	super(prefs.node(Filter.PREFS_TAG));

	JPanel pp = this.getPrefsPanel();

	pp.add(new JLabel(" Gear:"));
	this.gear = new JTextField(10);
	pp.add(this.gear);

	pp.add(new JLabel(" Minimum RPM:"));
	this.minRPM = new JTextField(10);
	pp.add(this.minRPM);

	pp.add(new JLabel(" Maximum RPM:"));
	this.maxRPM = new JTextField(10);
	pp.add(this.maxRPM);

	pp.add(new JLabel(" Minimum Pedal:"));
	this.minPedal = new JTextField(10);
	pp.add(this.minPedal);

	pp.add(new JLabel(" Minimum Throttle:"));
	this.minThrottle = new JTextField(10);
	pp.add(this.minThrottle);

	pp.add(new JLabel(" Minimum Points:"));
	this.minPoints = new JTextField(10);
	pp.add(this.minPoints);
    }

    public void updateDialog()
    {
	this.gear.setText("" + this.filter.gear());
	this.minRPM.setText("" + this.filter.minRPM());
	this.maxRPM.setText("" + this.filter.maxRPM());
	this.minPedal.setText("" + this.filter.minPedal());
	this.minThrottle.setText("" + this.filter.minThrottle());
	this.minPoints.setText("" + this.filter.minPoints());
    }

    public boolean showDialog(Component parent, String title, Filter filter) {
	this.filter = filter;
	return super.showDialog(parent, title);
    }
}