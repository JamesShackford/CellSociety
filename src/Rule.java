import java.util.Map;

import javafx.scene.control.Cell;
import javafx.scene.paint.Paint;

public interface Rule
{
	public Map<Integer, Paint> getStateMap();

	public int getNextState(Cell cell);
}
