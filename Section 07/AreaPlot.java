import java.awt.Color;
import java.util.Random;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.examples.ExamplePanel;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.areas.AreaRenderer;
import de.erichseifert.gral.plots.areas.DefaultAreaRenderer2D;
import de.erichseifert.gral.plots.areas.LineAreaRenderer2D;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.util.Insets2D;

public class AreaPlot extends ExamplePanel {
	/** Version id for serialization. */
	private static final long serialVersionUID = 3287044991898775949L;

	/** Instance to generate random data values. */
	private static final Random random = new Random();

	public AreaPlot() {
		// Generate data
		DataTable data = new DataTable(Double.class, Double.class, Double.class, Double.class);
		for (double x = 0.0; x < 50; x ++) {
			double y1 = Double.NaN, y2 = Double.NaN, y3 = Double.NaN;
			y1 = random.nextGaussian();
			y2 = random.nextGaussian();
			y3 = random.nextGaussian();
			data.add(x, y1, y2, y3);
		}

		// Create data series
		DataSeries data1 = new DataSeries("series 1", data, 0, 1);
		DataSeries data2 = new DataSeries("series 2", data, 0, 2);
		DataSeries data3 = new DataSeries("series 3", data, 0, 3);

		// Create new xy-plot
		XYPlot plot = new XYPlot(data1, data2, data3);
		plot.setLegendVisible(true);
		plot.setInsets(new Insets2D.Double(20.0, 40.0, 20.0, 20.0));

		// Format data series
		formatFilledArea(plot, data1, COLOR2);
		formatFilledArea(plot, data2, COLOR1);
		formatLineArea(plot, data3, GraphicsUtils.deriveDarker(COLOR1));

		// Add plot to Swing component
		add(new InteractivePanel(plot));
	}

	private static void formatFilledArea(XYPlot plot, DataSource data, Color color) {
		PointRenderer point = new DefaultPointRenderer2D();
		point.setColor(color);
		plot.setPointRenderer(data, point);
		LineRenderer line = new DefaultLineRenderer2D();
		line.setColor(color);
		line.setGap(3.0);
		line.setGapRounded(true);
		plot.setLineRenderer(data, line);
		AreaRenderer area = new DefaultAreaRenderer2D();
		area.setColor(GraphicsUtils.deriveWithAlpha(color, 64));
		plot.setAreaRenderer(data, area);
	}

	private static void formatLineArea(XYPlot plot, DataSource data, Color color) {
		PointRenderer point = new DefaultPointRenderer2D();
		point.setColor(color);
		plot.setPointRenderer(data, point);
		plot.setLineRenderer(data, null);
		AreaRenderer area = new LineAreaRenderer2D();
		area.setGap(3.0);
		area.setColor(color);
		plot.setAreaRenderer(data, area);
	}

	@Override
	public String getTitle() {
		return "Area plot";
	}

	@Override
	public String getDescription() {
		return "Area plot of three series with different styling";
	}

	public static void main(String[] args) {
		new AreaPlot().showInFrame();
	}
}
