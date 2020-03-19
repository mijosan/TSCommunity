window.onload = function(){
	//알림 기능 (롱폴링)
	var first = 0; //작성자한태도 안뜨게하며 새로고침 눌러도 안뜸
	var temp;

	(function poll() {
	    $.ajax({
	        url: '/boards/alarm',
	        type: 'post',
	        dataType: 'json', //받는 타입
	        contentType : "application/json", //보내는 타입
	        data : JSON.stringify({"writer":"하위2"}),
	        success: function(result) {

	            if(result.board != null && result.board.seq !=temp && first !=0){
	            	$("#snackbar").html("<img src='../img/newIcon2.png'>&nbsp;&nbsp;" + result.board.title);
	            	
	            	var link = "/boards/" + result.board.seq;
	            	
	            	$("#link2").attr("href",link);

	            	myFunction();
	            	
	            }
	            first = first + 1;
	            
	            temp = result.board.seq;
	            
	        },
	        error : function(){
	        	
	        },
	        timeout: 30000, // 반응이 없으면 30초후 종료
	        complete: setTimeout(function() { poll(); }, 6000) // 6초후에 다시 poll 함수 호출
	    });
	})();
}
function myFunction() {
	  // Get the snackbar DIV
	  var x = document.getElementById("snackbar");

	  // Add the "show" class to DIV
	  x.className = "show";

	  // After 3 seconds, remove the show class from DIV
	  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 8000);
	}