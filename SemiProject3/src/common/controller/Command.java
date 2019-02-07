package common.controller;

import javax.servlet.http.*;

public interface Command {
	// 
	void execute(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
