package Behavioral;

import java.util.Stack;

public class CommandManager {

	private Stack<Command> history = new Stack<>();
	
	public void executeCommand(Command command) {
		command.execute();
		history.push(command);
	}
		
	public void undoLastCommand() {
		if (!history.isEmpty()) {
			Command last = history.pop();
			last.undo();
		}
	}
}
