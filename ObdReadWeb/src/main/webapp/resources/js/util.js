function Verifica_Hora(campo){  
	   hrs = (campo.value.substring(0,2));  
	   min = (campo.value.substring(3,5));  
	   estado = "";  
	   if ((hrs < 00 ) || (hrs > 23) || ( min < 00) ||( min > 59)){  
	      estado = "errada";  
	   }  
	  
	   if (campo.value == "") {  
	      estado = "errada";  
	   }  
	   if (estado == "errada") {  
	      campo.value = '';
	      campo.focus();  
	   }  
	}

function isNumberKey(campo) {
	
	hrs = (campo.value.substring(0,2));  
	min = (campo.value.substring(3,5));  
	
	if ((hrs < 00 ) || (hrs > 23)){
		campo.value = '';
	}
	
	if(( min < 00) ||( min > 59)){
		campo.value = '';
	}

	
	/*
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode > 31 && (charCode < 44 || charCode > 57 || charCode==45 || charCode==46 || charCode==47)){
        return false;
    }
    else{
        var valor=elem.value;
        var tamanio=elem.value.length
        if (elem.value > 99){
            elem.value=99 + ',9';
            return false;
        }
        if(charCode == 44){
            if(valor.indexOf(',') < 0){
                if (tamanio > 0 && tamanio < 3){
                 return true;
                }
                else {
                    return false;
                }
            }
            else{
                return false;
            }
        }
    }
    */
}

function Mascara_Hora(Hora, campo){
	
	alert("Hora invalida!");
	
	   var hora01 = '';
	   hora01 = hora01 + Hora;
	   if (hora01.length == 2){
	      hora01 = hora01 + ':';
	      campo.value = hora01;
	   }
	   if (hora01.length == 5){
	      Verifica_Hora(campo);
	   }
}

function Verifica_Hora(campo){
	
	   hrs = (campo.value.substring(0,2));
	   min = (campo.value.substring(3,5));
	   estado = "";
	   if ((hrs < 00 ) || (hrs > 23) || ( min < 00) ||( min > 59)){
	      estado = "errada";
	   }

	   if (campo.value == "") {
	      estado = "errada";
	   }
	   if (estado == "errada") {
	      alert("Hora invalida!");
	      campo.focus();
	   }
} 

function remove_char_reinc_candidato(el) {
    var text = el.value;        
    el.value= text.replace(";", "")
            	.replace(":", "");
}
