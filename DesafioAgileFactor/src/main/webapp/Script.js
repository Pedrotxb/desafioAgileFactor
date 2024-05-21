/**
 * 
 */
//update product quantity
	function updateproduct(id) {
		$.ajax({
			type : "post",
			url : "Shopping", //this is my servlet
			data : "quantity=" + $('#quantity' + id).val() + "&prodid="
					+ $('#prodid' + id).val() + "&order_id="
					+ $('#order_id' + id).val() + "&cart=update",
			success : function(msg) {
				$.get("Shopping", function(data, status) {
					$("body").html(data);
					myPopup.classList.add("show");
				});

			}
		});
	}
	//remove product
	function removeproduct(id) {
		$.ajax({
			type : "post",
			url : "Shopping", //this is my servlet
			data : "&prodid=" + $('#prodid' + id).val() + "&order_id="
					+ $('#order_id' + id).val() + "&cart=remove",
			success : function(msg) {
				$.get("Shopping", function(data, status) {
					$("body").html(data);
					myPopup.classList.add("show");
				});
			}
		});
	}
	
	//Open productForm
	function openForm(idValue) {
		document.getElementById(idValue).style.display = "block";
	}
	//Close productForm
	function closeForm(idValue) {
		document.getElementById(idValue).style.display = "none";
	}
	
	//Increment productForm quantity
	function increment(input) {
		document.getElementById(input).stepUp();
	}
	//Decrement productForm quantity
	function decrement(input) {
		document.getElementById(input).stepDown();
	}
	
	//Autocomplete label search
	$(function() {
		$('#searchlabel').autocomplete({
			source : function(request, response) {
				//Fetch data
				$.ajax({
					url : "AutocompleteServlet",
					method : "post",
					dataType : 'json',
					data : {
						searchlabel : request.term
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
					//Add Label to list
					location.href = "Shopping?filter=add&&labelname=" + ui.item.value;
				}
			}
		});
	});

	//Autocomplete product search
	$(function() {
		$('#searchproduct').autocomplete({
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
	
	//Cart popup
	$(function() {
		cart.addEventListener("click", function() {
			myPopup.classList.add("show");
		}

		);
		closePopup.addEventListener("click", function() {
			myPopup.classList.remove("show");
		});
		window.addEventListener("click", function(event) {
			if (event.target == myPopup) {
				myPopup.classList.remove("show");
			}
		});
	});
	