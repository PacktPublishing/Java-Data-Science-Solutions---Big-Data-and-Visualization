import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.examples.ExamplePanel;
import de.erichseifert.gral.plots.PiePlot;
import de.erichseifert.gral.plots.PiePlot.PieSliceRenderer;
import de.erichseifert.gral.plots.colors.LinearGradient;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;


public class SimplePiePlot extends ExamplePanel {
	/** Version id for serialization. */
	private static final long serialVersionUID = -3039317265508932299L;

	private static final int SAMPLE_COUNT = 10;
	/** Instance to generate random data values. */
	private static Random random = new Random();

	@SuppressWarnings("unchecked")
	public SimplePiePlot() {
		// Create data
		DataTable data = new DataTable(Integer.class);
		for (int i = 0; i < SAMPLE_COUNT; i++) {
			int val = random.nextInt(8) + 2;
			data.add((random.nextDouble() <= 0.15) ? -val : val);
		}

		// Create new pie plot
		PiePlot plot = new PiePlot(data);

		// Format plot
		plot.getTitle().setText(getDescription());
		// Change relative size of pie
		plot.setRadius(0.9);
		// Display a legend
		plot.setLegendVisible(true);
		// Add some margin to the plot area
		plot.setInsets(new Insets2D.Double(20.0, 40.0, 40.0, 40.0));

		PieSliceRenderer pointRenderer =
				(PieSliceRenderer) plot.getPointRenderer(data);
		// Change relative size of inner region
		pointRenderer.setInnerRadius(0.4);
		// Change the width of gaps between segments
		pointRenderer.setGap(0.2);
		// Change the colors
		LinearGradient colors = new LinearGradient(COLOR1, COLOR2);
		pointRenderer.setColor(colors);
		// Show labels
		pointRenderer.setValueVisible(true);
		pointRenderer.setValueColor(Color.WHITE);
		pointRenderer.setValueFont(Font.decode(null).deriveFont(Font.BOLD));

		// Add plot to Swing component
		add(new InteractivePanel(plot), BorderLayout.CENTER);
	}

	@Override
	public String getTitle() {
		return "Donut plot";
	}

	@Override
	public String getDescription() {
		return String.format("Donut plot of %d random data values", SAMPLE_COUNT);
	}

	public static void main(String[] args) {
		new SimplePiePlot().showInFrame();
	}
}

