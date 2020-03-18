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
	            	alert(result.board.title);      	
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