package yang;


import yang.Utils.DFA;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnalysisServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
//        String json=req.getParameter("json");
//        String program= req.getParameter("program");
        String json="[\n" +
                "    {\n" +
                "        \"KeyWord\": \"program,function,procedure,array,const,file,record,set,type,var,case,of,begin,end,do,if,else,for,repeat,then,while,with,string,integer,class,not,read,write\",\n" +
                "        \"Character\": \"a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9,+,-,*,/,<,=,(,),[,],{,},:,.,;,‘\",\n" +
                "        \"SingleDelimiter\": \"+,|,-,|,*,|,/,|,<,|,=,|,(,|,),|,[,|,],|,.,|,;,|,:\"\n" +
                "    }\n" +
                "]\n";
        String program="program p;\n" +
                "var i : integer ;\n" +
                "     x : array[1..16] of integer ;\n" +
                "begin\n" +
                "  i := 5 + 10 ;\n" +
                "  x[i] := 0 ;\n" +
                "end .\n";
        DFA dfa=new DFA();
        dfa.dfa(program,json);
        String result=dfa.Result();
        resp.getWriter().write(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
