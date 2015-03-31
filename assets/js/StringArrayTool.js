// JavaScript Document

function getMax(){
	var maxStr = this[0];
	
	for(var i=1; i<this.length; i++){
		if(maxStr < this[i]){
			var temp = this[i];
			this[i] = maxStr;
			maxStr = temp;
		}
	}
	return maxStr;
}

function getMin(){
	var minStr = 0;
	
	for(var j=1; j<this.length; j++){
	
		if(this[minStr] > this[j]){
			minStr = j;
		}
	}
	return this[minStr];
}

/*第一种排序 选择排序*/
function arrSort1(){
	/*
	for(var i=0; i<this.length-1; i++){
		var mins = i;
		for(var j=i+1; j<this.length; j++){			
			if(this[mins] > this[j]){				
				mins = j;				
			}
		}
		if(i != mins){
			var temp = arr[mins];
			this[mins] = this[i];
			this[i] = temp;
		}
	}	*/
	for (var i = 0; i < this.length; i++) {  
            var min = i; /* 将当前下标定义为最小值下标 */  
  
            for (var j = i + 1; j < this.length; j++) {  
                if (this[min] > this[j]) { /* 如果有小于当前最小值的关键字 */  
                    min = j; /* 将此关键字的下标赋值给min */  
                }  
            }  
            if (i != min) {/* 若min不等于i，说明找到最小值，交换 */  
                var tmp = this[min];  
                this[min] = this[i];  
                this[i] = tmp;  
            }  
        } 	
	return this;
}

function get99Method(){
	
	document.write("<table >");
	for(var i=1; i<10; i++){
		document.write("<tr>");
		for(var j=1; j<=i; j++){
			document.write("<th>" + j + "*" + i + "=" + j*i + "</th>");
		}
		document.write("</tr>");
	}
	document.write("</table>");	
}


Array.prototype.getMax = getMax;
Array.prototype.getMin = getMin;
Array.prototype.arrSort1 = arrSort1;