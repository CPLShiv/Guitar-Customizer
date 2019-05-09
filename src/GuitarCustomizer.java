//Caleb Garcia

//Imports
import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.text.DecimalFormat;

public class GuitarCustomizer extends JFrame implements ActionListener {
	//Create GUI components
	JPanel panel = new JPanel();
	
	//Customer name label and text field
	JLabel custNameLbl = new JLabel("Name:");
	JTextField custNameTxt = new JTextField();
	
	//Define body box components
	JLabel bodyLbl = new JLabel("Body Style:");
	JRadioButton stratBtn = new JRadioButton("Stratocaster - base price ~$250");
	JRadioButton gibsBtn = new JRadioButton("Gibson Classic - base price ~$275");
	JRadioButton flyingBtn = new JRadioButton("Flying V - base price ~$300");
	ButtonGroup bodyGroup = new ButtonGroup();
	
	//Define pickup box components
	JLabel pickupLbl = new JLabel("Pickups ($50 each):");
	JRadioButton pick1Btn = new JRadioButton("1");
	JRadioButton pick2Btn = new JRadioButton("2");
	JRadioButton pick3Btn = new JRadioButton("3");
	ButtonGroup pickupsGroup = new ButtonGroup();
	
	//Define knobs box components
	JLabel knobsLbl = new JLabel("Tone knobs ($25 each):");
	JRadioButton knob1Btn = new JRadioButton("1");
	JRadioButton knob2Btn = new JRadioButton("2");
	JRadioButton knob3Btn = new JRadioButton("3");
	ButtonGroup knobsGroup = new ButtonGroup();
	
	//Define color box components and map
	JLabel colorLbl = new JLabel("Body color:");
	JRadioButton blackBtn = new JRadioButton("Black");
	JRadioButton blueBtn = new JRadioButton("Blue");
	JRadioButton redBtn = new JRadioButton("Red");
	JRadioButton metalBlueBtn = new JRadioButton("Metallic Blue (add $50)");
	JRadioButton metalSilverBtn = new JRadioButton("Metallic Silver (add $50)");
	JRadioButton charcoalBtn = new JRadioButton("Charcoal (add $100)");
	ButtonGroup colorGroup = new ButtonGroup();
	HashMap<String, Double> colorMap = new HashMap<String, Double>();
	
	//Define board box components
	JLabel boardLbl = new JLabel("Fretboard:");
	JRadioButton mapleBtn = new JRadioButton("Maple");
	JRadioButton ebonyBtn = new JRadioButton("Ebony (add $50)");
	JRadioButton rosewoodBtn = new JRadioButton("Rosewood (add $100)");
	ButtonGroup boardGroup = new ButtonGroup();
	
	//Define buttons
	JButton estimateBtn = new JButton("Get Estimate");
	JButton startOverBtn = new JButton("Start Over");
	JButton exitBtn = new JButton("Exit");
	
	//Define constants
	final static int WINDOW_WIDTH = 600;
	final static int WINDOW_HEIGHT = 600;
	final static int BUTTON_WIDTH = WINDOW_WIDTH / 5;
	final static int BUTTON_HEIGHT = WINDOW_HEIGHT - 70;
	final static double SALES_TAX = .0725;
	
	//Initialize price variables
	double bodyPrice = 0;
	double pickupPrice = 0;
	double knobPrice = 0;
	double colorPrice = 0;
	double boardPrice = 0;
	
	//Define other variables
	double estimate;
	double estimateTax;
	double estimateWithTax;
	String receipt = "";
	
	public GuitarCustomizer() {
		//Frame and panel settings
		pack();
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setTitle("Guitar Customizer");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setLayout(null);
		getContentPane().add(panel);
		
		//Customer name component placement
		custNameLbl.setBounds(10, 10, 40, 15);
		panel.add(custNameLbl);
		custNameTxt.setBounds(60, 10, 80, 20);
		panel.add(custNameTxt);
		
		//Build boxes
		buildBodyBox(20);
		buildPickupBox((WINDOW_WIDTH / 2) + 20);
		buildKnobBox(20);
		buildColorBox((WINDOW_WIDTH / 2) + 20);
		buildBoardBox(20);
		
		//Radio settings
		//Body style button group
		bodyGroup.add(stratBtn);
		bodyGroup.add(gibsBtn);
		bodyGroup.add(flyingBtn);
		
		//Pickups button group
		pickupsGroup.add(pick1Btn);
		pickupsGroup.add(pick2Btn);
		pickupsGroup.add(pick3Btn);
		
		//Knobs button group
		knobsGroup.add(knob1Btn);
		knobsGroup.add(knob2Btn);
		knobsGroup.add(knob3Btn);
		
		//Color button group
		colorGroup.add(blackBtn);
		colorGroup.add(blueBtn);
		colorGroup.add(redBtn);
		colorGroup.add(metalBlueBtn);
		colorGroup.add(metalSilverBtn);
		colorGroup.add(charcoalBtn);
		//colorMap.put(blackBtn.getText(), 0.0);
		//colorMap.put(blueBtn.getText(), 0.0);
		//colorMap.put(redBtn.getText(), 0.0);
		colorMap.put(metalBlueBtn.getText(), 50.0);
		colorMap.put(metalSilverBtn.getText(), 50.0);
		colorMap.put(charcoalBtn.getText(), 100.0);
		
		//Board button group
		boardGroup.add(mapleBtn);
		boardGroup.add(ebonyBtn);
		boardGroup.add(rosewoodBtn);
		
		//Utility button placements
		estimateBtn.setBounds(20, BUTTON_HEIGHT, BUTTON_WIDTH, 30);
		panel.add(estimateBtn);
		
		startOverBtn.setBounds((WINDOW_WIDTH / 2) - (BUTTON_WIDTH / 2), BUTTON_HEIGHT, BUTTON_WIDTH, 30);
		panel.add(startOverBtn);
		
		exitBtn.setBounds((WINDOW_WIDTH - BUTTON_WIDTH - 20), BUTTON_HEIGHT, BUTTON_WIDTH, 30);
		panel.add(exitBtn);
		
		estimateBtn.addActionListener(this);
		startOverBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		
		stratBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					rosewoodBtn.setVisible(false);
				}
			}
		});
		gibsBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					metalBlueBtn.setVisible(false);
					metalSilverBtn.setVisible(false);
					rosewoodBtn.setVisible(true);
				}
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					metalBlueBtn.setVisible(true);
					metalSilverBtn.setVisible(true);
				}
			}
		});
		flyingBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					charcoalBtn.setVisible(false);
					rosewoodBtn.setVisible(false);
				}
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					charcoalBtn.setVisible(true);
				}
			}
		});
	}
	
	private void buildBodyBox(int left) {
		bodyLbl.setBounds(left, 50, 70, 15);
		stratBtn.setBounds(left, 70, 210, 20);
		gibsBtn.setBounds(left, 100, 220, 20);
		flyingBtn.setBounds(left, 130, 180, 20);
		panel.add(bodyLbl);
		panel.add(stratBtn);
		panel.add(gibsBtn);
		panel.add(flyingBtn);
		
	}
	
	private void buildPickupBox(int left) {
		pickupLbl.setBounds(left, 50, 120, 15);
		pick1Btn.setBounds(left, 70, 40, 20);
		pick2Btn.setBounds(left, 100, 40, 20);
		pick3Btn.setBounds(left, 130, 40, 20);
		panel.add(pickupLbl);
		panel.add(pick1Btn);
		panel.add(pick2Btn);
		panel.add(pick3Btn);
	}
	
	private void buildKnobBox(int left) {
		knobsLbl.setBounds(left, 180, 140, 15);
		knob1Btn.setBounds(left, 200, 40, 20);
		knob2Btn.setBounds(left, 230, 40, 15);
		knob3Btn.setBounds(left, 260, 40, 15);
		panel.add(knobsLbl);
		panel.add(knob1Btn);
		panel.add(knob2Btn);
		panel.add(knob3Btn);
	}
	
	private void buildColorBox(int left) {
		colorLbl.setBounds(left, 180, 70, 15);
		blackBtn.setBounds(left, 200, 60, 20);
		blueBtn.setBounds(left, 230, 50, 20);
		redBtn.setBounds(left, 260, 50, 20);
		metalBlueBtn.setBounds(left, 290, 160, 20);
		metalSilverBtn.setBounds(left, 320, 160, 20);
		charcoalBtn.setBounds(left, 350, 140, 20);
		panel.add(colorLbl);
		panel.add(blackBtn);
		panel.add(blueBtn);
		panel.add(redBtn);
		panel.add(metalBlueBtn);
		panel.add(metalSilverBtn);
		panel.add(charcoalBtn);
	}
	
	private void buildBoardBox(int left) {
		boardLbl.setBounds(left, 290, 100, 15);
		mapleBtn.setBounds(left, 310, 60, 20);
		ebonyBtn.setBounds(left, 340, 120, 20);
		rosewoodBtn.setBounds(left, 370, 150, 20);
		panel.add(boardLbl);
		panel.add(mapleBtn);
		panel.add(ebonyBtn);
		panel.add(rosewoodBtn);
		
	}
	
	private double getBodyEstimate() {
		if (stratBtn.isSelected()) {
			bodyPrice = 250;
			receipt += stratBtn.getText() + "\n";
		} else if (gibsBtn.isSelected()) {
			bodyPrice = 275;
			receipt += gibsBtn.getText() + "\n";
		} else if (flyingBtn.isSelected()) {
			bodyPrice = 300;
			receipt += flyingBtn.getText() + "\n";
		}
		
		return bodyPrice;
	}
	
	private double getPickupEstimate() {
		if (pick1Btn.isSelected()) {
			pickupPrice = 50;
			receipt += "1 pickup\n";
		} else if (pick2Btn.isSelected()) {
			pickupPrice = 100;
			receipt += "2 pickups\n";
		} else if (pick3Btn.isSelected()) {
			pickupPrice = 150;
			receipt += "3 pickups\n";
		}
		
		return pickupPrice;
	}
	
	private double getKnobsEstimate() {
		if (knob1Btn.isSelected()) {
			knobPrice = 25;
			receipt += "1 knob\n";
		} else if (knob2Btn.isSelected()) {
			knobPrice = 50;
			receipt += "2 knobs\n";
		} else if (knob3Btn.isSelected()) {
			knobPrice = 75;
			receipt += "3 knobs\n";
		}
		
		return knobPrice;
	}
	
	private double getColorEstimate() {
		if (blackBtn.isSelected()) {
			receipt += blackBtn.getText() + "\n";
		} else if (blueBtn.isSelected()) {
			receipt += blueBtn.getText() + "\n";
		} else if (redBtn.isSelected()) {
			receipt += redBtn.getText() + "\n";
		} else if (metalBlueBtn.isSelected()) {
			colorPrice = colorMap.get(metalBlueBtn.getText());
			receipt += metalBlueBtn.getText() + "\n";
		} else if (metalSilverBtn.isSelected()) {
			colorPrice = colorMap.get(metalSilverBtn.getText());
			receipt += metalSilverBtn.getText() + "\n";
		} else if (charcoalBtn.isSelected()) {
			colorPrice = colorMap.get(charcoalBtn.getText());
			receipt += charcoalBtn.getText() + "\n";
		}
		
		return colorPrice;
	}
	
	private double getBoardEstimate() {
		if (mapleBtn.isSelected()) {
			receipt += mapleBtn.getText() + "\n";
		}
		if (ebonyBtn.isSelected()) {
			boardPrice = 50;
			receipt += ebonyBtn.getText() + "\n";
		} else if (rosewoodBtn.isSelected()) {
			boardPrice = 100;
			receipt += rosewoodBtn.getText() + "\n";
		}
		
		return boardPrice;
	}
	
	public boolean isOrderValid() {		
		//Check if no body style is selected, handle
		if (!stratBtn.isSelected() && !gibsBtn.isSelected() && !flyingBtn.isSelected()) {
			JOptionPane.showMessageDialog(null, "Please select a body style.");
			return false;
		}
		
		//Check if no # of pickups is selected, handle
		if (!pick1Btn.isSelected() && !pick2Btn.isSelected() && !pick3Btn.isSelected()) {
			JOptionPane.showMessageDialog(null, "Please select a pickup amount.");
			return false;
		}
		
		//Check if no # of tone knobs is selected, handle
		if (!knob1Btn.isSelected() && !knob2Btn.isSelected() && !knob3Btn.isSelected()) {
			JOptionPane.showMessageDialog(null, "Please select a tone knob amount.");
			return false;
		}
		
		//Check if no body color is selected, handle
		if (!blackBtn.isSelected() && !blueBtn.isSelected() && !redBtn.isSelected() && !metalBlueBtn.isSelected() && !metalSilverBtn.isSelected() && !charcoalBtn.isSelected()) {
			JOptionPane.showMessageDialog(null, "Please select a body color.");
			return false;
		}
		
		//Check if no fretboard wood is selected, handle
		if (!mapleBtn.isSelected() && !ebonyBtn.isSelected() && !rosewoodBtn.isSelected()) {
			JOptionPane.showMessageDialog(null, "Please select a fretboard wood.");
			return false;
		}
		
		//Check if more tone knobs than pickups
		if (knob2Btn.isSelected() && pick1Btn.isSelected()) {
			JOptionPane.showMessageDialog(null, "You cannot order more tone knobs than pickups.");
			return false;
		} else if (knob3Btn.isSelected() && pick2Btn.isSelected()) {
			JOptionPane.showMessageDialog(null, "You cannot order more tone knobs than pickups.");
			return false;
		}
		
		return true;
	}
	
	public void getTotals() {
		estimate = getEstimate();
		
		estimateTax = getEstimateTax(estimate);
		estimateWithTax = getEstimateWithTax(estimate, estimateTax);
	}
	
	private double getEstimate() {
		double bodyEst = getBodyEstimate();
		double pickupEst = getPickupEstimate();
		double knobsEst = getKnobsEstimate();
		double colorEst = getColorEstimate();
		double boardEst = getBoardEstimate();
		
		estimate = bodyEst + pickupEst + knobsEst + colorEst + boardEst;
		
		return estimate;
	}
	
	private double getEstimateTax(double estimate) {
		double estimateTax = estimate * SALES_TAX;
		return estimateTax;
	}
	
	private double getEstimateWithTax(double estimate, double estimateTax) {
		estimateWithTax = estimate + estimateTax;
		return estimateWithTax;
	}
	
	private void printReceipt(double estimate, double estimateTax, double estimateWithTax) {
		receipt += "Estimate: " + String.format("%.2f", estimate) + "\nTax: " + String.format("%.2f", estimateTax) + "\nTotal estimate: " + String.format("%.2f", estimateWithTax) ;
		receipt += "\n" + custNameTxt.getText() + "'s Guitar Order";
		JOptionPane.showMessageDialog(null, receipt);
	}
	
	private void startOver() {
		custNameTxt.setText(null);
		bodyGroup.clearSelection();
		pickupsGroup.clearSelection();
		knobsGroup.clearSelection();
		colorGroup.clearSelection();
		boardGroup.clearSelection();
	}
	
	private void exit() {
		JOptionPane.showMessageDialog(null, "Thank you for using the Guitar Customizer!");
		System.exit(0);
	}
	
	//Method for handling button actions
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		
		if (source.equals(estimateBtn)) {
			if (isOrderValid()) {
				getTotals();
				printReceipt(estimate, estimateTax, estimateWithTax);
			}
		} else if (source.equals(startOverBtn)) {
			startOver();
		} else if (source.equals(exitBtn)) {
			exit();
		}
		
	}
	
	//Main method
	public static void main(String[] args) {
		GuitarCustomizer customizer = new GuitarCustomizer();
	}
	
}