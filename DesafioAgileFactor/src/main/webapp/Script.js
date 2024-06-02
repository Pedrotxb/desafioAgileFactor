	function updateLabelCount() {
        var labelCount = $('#labels-list li').length;
        $('#show-sidebar-btn').append('<span class="badge">' + labelCount + '</span>');
    }
//remove label
	function removeLabel(name) {
		$.ajax({
			type : "post",
			url : "Shopping",
			data : {
				filter: "remove",
				labelname: name
			},
			success : function(msg) {
				$.get("Shopping?dispatcher=labelpopup", function(data, status) {
						getProducts(); 
					 	$('#labels-list').html(data);
					 	updateLabelCount($('#labels-list li').length-1);					
				});
			}
		});
	}
	
	function getProducts() {
		$.ajax({
			type : "post",
			url : "Shopping",
			data : {
				dispatcher: "listproducts"			
			},
			success : function(data, status) {
					 	$('#product-container').html(data);
			}
		});
	}
	
/**
 * 
 */
//update product quantity
	function updateproduct(id) {
		var quantity = document.getElementById('quantity'+id).value;
		if (quantity < 1){
			quantity = 1;
		}	

    	const prodid = document.getElementById('prodid'+id+'u').value;
    	const order_id = document.getElementById('order_id'+id+'u').value;
		$.ajax({
			type : "post",
			url : "Shopping", 
			data : {
					quantity : quantity,
					prodid : prodid,
					order_id : order_id,
					cart : "update"
				},
			success : function(msg) {
				$.get("Shopping?dispatcher=cartpopup", function(data, status) {
					 $('#cart-popup').html(data);
				});

			}
		});
	}
	//remove product
	function removeproduct(id) {
		$.ajax({
			type : "post",
			url : "Shopping", 
			data : "&prodid=" + $('#prodid' + id).val() + "&order_id="
					+ $('#order_id' + id).val() + "&cart=remove",
			success : function(msg) {
				$.get("Shopping?dispatcher=cartpopup", function(data, status) {
					 $('#cart-popup').html(data);
					 document.getElementById('cart-count').textContent -= 1;
				});
			}
		});
	}
	
	function closePopup(){
		document.getElementById('cart-popup').style.display = 'none';
	}
	
	function decrementQuantity(id) {
    const input = document.getElementById(`quantity${id}`);
    let value = parseInt(input.value);
    if (value > parseInt(input.min)) {
        value--;
        input.value = value;
    }
    updateproduct(id);
}

function incrementQuantity(id) {
    const input = document.getElementById(`quantity${id}`);
    let value = parseInt(input.value);
    if (value < parseInt(input.max)) {
        value++;
        input.value = value;
    }
    updateproduct(id);
}
	
	
document.addEventListener('DOMContentLoaded', () => {	
    const cartIcon = document.getElementById('cart-icon');
	const toggleSidebarBtn = document.getElementById("toggle-sidebar");
    const showSidebarBtn = document.getElementById("show-sidebar-btn");
    const sidebar = document.getElementById("sidebar");

    cartIcon.addEventListener('click', () => {
        document.getElementById('cart-popup').style.display = 'block';
    });

	
	
	//Autocomplete label search
	$(function() {
		$('#search-label').autocomplete({
        source: function(request, response) {
            // Fetch data
            $.ajax({
                url: "AutocompleteServlet",
                method: "post",
                dataType: 'json',
                data: {
                    searchlabel: request.term
                },
                success: function(data) {
                    response(data);
                }
            });
        },
        minLength: 0,
        scroll: true,
        select: function(event, ui) {
            console.log(ui.item.value);
            if (ui.item.value != "") {
                // Add Label to list
                $.ajax({
                    url: "Shopping",
                    method: "post",
                    data: {
                        filter: "add",
                        labelname: ui.item.value
                    },
                    success: function(response) {
                         $('#labels-list').append(
							'<p>'+
                            '<li>' +                  
							'<a>'+ui.item.value+'</a>'+
							'<button onclick="removeLabel('+name+')">&#128473;</button></li>'+
							'</p>'
                        );
                        // Update label count
                        updateLabelCount();   
                        //Generate products   
                        getProducts();      
                        document.getElementById("search-label").value="";
                    }          
                });
            }
        }
    });
		
		

    // Initial update of label count
    updateLabelCount();
	});

	//Autocomplete product search
	$(function() {
		$('#search-product').autocomplete({
			source : function(request, response) {
				//Fetch data
				$.ajax({
					url : "AutocompleteServlet",
					method : "post",
					dataType : 'json',
					data : {
						searchproduct : request.term
					},
					success : function(data) {
						response(data);
					}
				});
			},
			minLength : 0,
			scroll : true,
			select : function(event, ui) {
				console.log(ui.item.value);
				if (ui.item.value != "") {
					location.href = "Shopping?byname=" + ui.item.value;
				}
			}

		});
	});

   
     toggleSidebarBtn.addEventListener("click", () => {
        if (sidebar.classList.contains("hidden")) {
            sidebar.classList.remove("hidden");
            toggleSidebarBtn.innerText = "\u{1F5D9}";
            showSidebarBtn.style.display = "none";
        } else {
            sidebar.classList.add("hidden");
            toggleSidebarBtn.innerText = "\u{1F5D9}";
            showSidebarBtn.style.display = "block";
            showSidebarBtn.classList.add("hidden-sidebar");
        }
    });

    showSidebarBtn.addEventListener("click", () => {
        sidebar.classList.remove("hidden");
        toggleSidebarBtn.innerText = "\u{1F5D9}";
        showSidebarBtn.style.display = "none";
    });
});	