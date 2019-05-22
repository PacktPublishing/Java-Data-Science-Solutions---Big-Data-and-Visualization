import java.util.Random;

import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.EnumeratedData;
import de.erichseifert.gral.data.statistics.Histogram1D;
import de.erichseifert.gral.data.statistics.Statistics;
import de.erichseifert.gral.examples.ExamplePanel;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.util.Insets2D;
import de.erichseifert.gral.util.MathUtils;
import de.erichseifert.gral.util.Orientation;


public class HistogramPlot extends ExamplePanel {
	/** Version id for serialization. */
	private static final long serialVersionUID = 4458280577519421950L;

	private static final int SAMPLE_COUNT = 1000;

	//@SuppressWarnings("unchecked")
	public HistogramPlot() {
		// Create example data
		Random random = new Random();
		DataTable data = new DataTable(Double.class);
		for (int i = 0; i < SAMPLE_COUNT; i++) {
			data.add(random.nextGaussian());
		}

		// Create histogram from data
		Histogram1D histogram = new Histogram1D(data, Orientation.VERTICAL,
				new Number[] {-4.0, -3.2, -2.4, -1.6, -0.8, 0.0, 0.8, 1.6, 2.4, 3.2, 4.0});
		// Create a second dimension (x axis) for plotting
		DataSource histogram2d = new EnumeratedData(histogram, (-4.0 + -3.2)/2.0, 0.8);

		// Create new bar plot
		BarPlot plot = new BarPlot(histogram2d);

		// Format plot
		plot.setInsets(new Insets2D.Double(20.0, 65.0, 50.0, 40.0));
		plot.getTitle().setText(
				String.format("Distribution of %d random samples", data.getRowCount()));
		plot.setBarWidth(0.78);

		// Format x axis
		plot.getAxisRenderer(BarPlot.AXIS_X).setTickAlignment(0.0);
		plot.getAxisRenderer(BarPlot.AXIS_X).setTickSpacing(0.8);
		plot.getAxisRenderer(BarPlot.AXIS_X).setMinorTicksVisible(false);
		// Format y axis
		plot.getAxis(BarPlot.AXIS_Y).setRange(0.0,
				MathUtils.ceil(histogram.getStatistics().get(Statistics.MAX)*1.1, 25.0));
		plot.getAxisRenderer(BarPlot.AXIS_Y).setTickAlignment(0.0);
		plot.getAxisRenderer(BarPlot.AXIS_Y).setMinorTicksVisible(false);
		plot.getAxisRenderer(BarPlot.AXIS_Y).setIntersection(-4.4);

		// Format bars
		plot.getPointRenderer(histogram2d).setColor(
			GraphicsUtils.deriveWithAlpha(COLOR1, 128));
		plot.getPointRenderer(histogram2d).setValueVisible(true);

		// Add plot to Swing component
		InteractivePanel panel = new InteractivePanel(plot);
		panel.setPannable(false);
		panel.setZoomable(false);
		add(panel);
	}

	@Override
	public String getTitle() {
		return "Histogram plot";
	}

	@Override
	public String getDescription() {
		return String.format("Histogram of %d samples", SAMPLE_COUNT);
	}

	public static void main(String[] args) {
		new HistogramPlot().showInFrame();
	}
}
