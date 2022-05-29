let analyResultArray = []
async function request(method, url, data) {
    const res = await fetch(url, {
        method,
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        },
        body: JSON.stringify(data)
    })
    const json = await res.json()
    return json
}
let navBox = document.querySelector('.main-nav>ul')
let showBox = document.querySelector('main')
let analyBtn = document.querySelector('#analy-start')
let openfileBtn = document.querySelector('#analy-openfile')
let textarea = document.querySelector('.analy-textarea>textarea')
let inputOpen = document.querySelector('.inputopenfile')
let tableBody = document.querySelector('.analy-table tbody')
//绑定事件
for (let i = 0, len = navBox.children.length; i < len; i++) {
    console.log(navBox.children[i])
    navBox.children[i].onclick = function () {
        console.log(i)
        for (let j = 0; j < len; j++) {
            navBox.children[j].classList.remove('li-active')
            showBox.children[j].style.display = "none"
        }
        showBox.children[i].style.display = "block"
        navBox.children[i].classList.add('li-active')
    }
}
//渲染表格函数
function renderTable(arr) {
    let result = ""
    for (let i = 0; i < arr.length; i++) {
        result += `<tr><td>${i + 1}</td><td>${arr[i][0]}</td><td>${arr[i][1]}</td></tr>`
    }
    return result
}
analyBtn.addEventListener('click', () => {
    let content = textarea.value
    if (content === "") {
        alert("输入为空！请重新输入")
        return
    }
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/dfa',
        data: {
            json: `[
                {
                    "KeyWord": "program,function,procedure,array,const,file,record,set,type,var,case,of,begin,end,do,if,else,for,repeat,then,while,with,string,integer,class,not,read, write",
                    "Character": "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9,+,-,*,/,<,=,(,),[,],{,},:,.,;,‘",
                    "SingleDelimiter": "+,-,*,/,<,=,(,),[,],.,;,|,:"
                }
            ]`,
            program: content
        },
        success: function (res) {
            res = res.replace('{', '').replace('}', '').replaceAll('"', '')
            const arr = res.split(',')
            for (let i = 0; i < arr.length; i++) {
                arr[i] = arr[i].split(":")
            }
            analyResultArray = arr
            console.log(analyResultArray)
            let str = renderTable(analyResultArray)
            tableBody.innerHTML = str
        }
    })

})
