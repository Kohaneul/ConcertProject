 function plusCpage(){
         let cpage = document.getElementById('cpage').value;
         cpage=Number(cpage)+1;
         document.getElementById('cpage').value = cpage;
         };

 function downCpage(){
          let cpage = document.getElementById('cpage').value;
          cpage=Number(cpage)-1;
          if(Number(cpage)<=1){
             alert("1 미만으로 페이지 설정이 불가합니다.");
             document.getElementById('cpage').value = 1;
             return;
          }
          document.getElementById('cpage').value = cpage;
          };