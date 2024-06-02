<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var = "labels" value ="${sessionScope.labels}"/>
		<c:if test="${labels != null}">			
			<c:forEach var="label" items="${labels}">
				<p>
				<li><a>${label.name}</a>
				<button onclick="removeLabel('${label.name}')">&#128473;</button></li>
				</p>
				</form>
			</c:forEach>
		</c:if>
