package cz.muni.fi.pv168.agentmanagerweb;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.pv168.gmiterkosys.Agent;
import cz.muni.fi.pv168.gmiterkosys.AgentManager;
import cz.muni.fi.pv168.gmiterkosys.ServiceFailureException;

/**
 *
 * @author Dominik Gmiterko
 */
@WebServlet(AgentsServlet.URL_MAPPING + "/*")
public class AgentsServlet extends HttpServlet {

    public final static String URL_MAPPING = "/agents";
    private final static String LIST_JSP = "/list.jsp";
    private final static String SINGLE_JSP = "/single.jsp";
    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //display single agnet when ID is provided
        try {
            long id = Long.valueOf(request.getParameter("id"));

            if (id > 0) {
                Agent agent = getAgentManager().getAgentById(id);
                if (agent != null) {
                    request.setAttribute("agent", agent);
                    request.getRequestDispatcher(SINGLE_JSP).forward(request, response);
                    return;
                }
            }
        } catch (NumberFormatException e) {
        }

        //show list of all agents
        showAgentsList(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        switch (action) {
            case "add":
                actionAdd(request, response);
                break;
            case "delete":
                actionDelete(request, response);
                break;
            case "edit":
                actionEdit(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
    }

    private AgentManager getAgentManager() {
        return (AgentManager) getServletContext().getAttribute("agentManager");
    }

    private void showAgentsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String error = (String) request.getSession().getAttribute("error");
            if (error != null) {
                request.setAttribute("error", error);
                request.getSession().removeAttribute("error");
            }

            request.setAttribute("agents", getAgentManager().findAllAgents());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServiceFailureException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void actionAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //getting POST parameters from form
        String name = request.getParameter("name");

        if (name == null || name.length() == 0) {
            request.getSession().setAttribute("error", "Je nutne vyplnit meno!");
            response.sendRedirect(request.getContextPath() + URL_MAPPING);
            return;
        }

        LocalDate born = null;
        LocalDate died = null;

        try {
            String bornString = request.getParameter("born");
            if (bornString != null && !bornString.isEmpty()) {
                born = LocalDate.parse(bornString, DATE_FORMAT);
            }

            String diedString = request.getParameter("died");
            if (diedString != null && !diedString.isEmpty()) {
                died = LocalDate.parse(diedString, DATE_FORMAT);
            }
        } catch (DateTimeParseException e) {
            request.getSession().setAttribute("error", "Datum je v nespravnom tvare!");
            response.sendRedirect(request.getContextPath() + URL_MAPPING);
            return;
        }

        int level;
        try {
            level = Integer.parseInt(request.getParameter("level"));
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Level musi byt ciselna hodnota!");
            response.sendRedirect(request.getContextPath() + URL_MAPPING);
            return;
        }
        //form data processing - storing to database
        try {
            Agent agent = new Agent();

            agent.setName(name);
            agent.setBorn(born);
            agent.setDied(died);
            agent.setLevel(level);

            getAgentManager().createAgent(agent);

            response.sendRedirect(request.getContextPath() + URL_MAPPING);
            return;
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + URL_MAPPING);
            return;
        } catch (ServiceFailureException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;
        }
    }

    private void actionDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            Long id = Long.valueOf(request.getParameter("id"));
            Agent agent = getAgentManager().getAgentById(id);
            if (agent != null) {
                getAgentManager().removeAgent(agent);
                response.sendRedirect(request.getContextPath() + URL_MAPPING);
                return;
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Agent with id " + id + " doesn't exist");
                return;
            }
        } catch (ServiceFailureException | IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;
        }
    }

    private void actionEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //getting POST parameters from form
        Long id = Long.valueOf(request.getParameter("id"));
        Agent agent = getAgentManager().getAgentById(id);
        if (agent == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Agent with id " + id + " doesn't exist");
            return;
        }

        String name = request.getParameter("name");

        if (name == null || name.length() == 0) {
            request.getSession().setAttribute("error", "Je nutne vyplnit meno!");
            response.sendRedirect(request.getContextPath() + URL_MAPPING);
            return;
        }

        LocalDate born = null;
        LocalDate died = null;

        try {
            String bornString = request.getParameter("born");
            if (bornString != null && !bornString.isEmpty()) {
                born = LocalDate.parse(bornString, DATE_FORMAT);
            }

            String diedString = request.getParameter("died");
            if (diedString != null && !diedString.isEmpty()) {
                died = LocalDate.parse(diedString, DATE_FORMAT);
            }
        } catch (DateTimeParseException e) {
            request.getSession().setAttribute("error", "Datum je v nespravnom tvare!");
            response.sendRedirect(request.getContextPath() + URL_MAPPING);
            return;
        }

        int level;
        try {
            level = Integer.parseInt(request.getParameter("level"));
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Level musi byt ciselna hodnota!");
            response.sendRedirect(request.getContextPath() + URL_MAPPING);
            return;
        }
        //form data processing - storing to database
        try {
            agent.setName(name);
            agent.setBorn(born);
            agent.setDied(died);
            agent.setLevel(level);

            getAgentManager().updateAgent(agent);

            response.sendRedirect(request.getContextPath() + URL_MAPPING);
            return;
        } catch (ServiceFailureException | IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;
        }
    }

}
