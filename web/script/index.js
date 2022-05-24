$.ajax({
    type: 'GET',
    url: 'http://localhost:8080/dfa',
    data: {
        "json": [
            {
                "KeyWord": "program,function,procedure,array,const,file,record,set,type,var,case,of,begin,end,do,if,else,for,repeat,then,while,with,string,integer,class,not,read, write",
                "Character": "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9,+,-,*,/,<,=,(,),[,],{,},:,.,;,‘",
                "singleDelimiter": "+,-,*,/,<,=,(,),[,],.,;,|,:"
            }
        ],
        "program": `
        int
        `
    },
    success: function (res) {
        console.log(res)
    }
})