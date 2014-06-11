<html>
<head>
    <meta name="layout" content="main" />
    <title>What Are You Doing?</title>
    <r:require modules="bootstrap"/>
    <g:javascript library="jquery" plugin="jquery" />
</head>
<body>

	<h3>&nbsp;&nbsp;&nbsp;Search For People To Follow</h3>
	<div class="searchForm">
   		<g:form controller="searchable">
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<g:textField name="q" value=""/>
    	</g:form>
	</div>
	<br/>

	<div class ="well">
    <h2>What Are You Doing?</h2> <font color="grey"><p>(2 to 140 characters)</p></font>
    <div class="updateStatusForm">
        <g:formRemote onSuccess="document.getElementById('messageArea').value='';" url="[action: 'updateStatus']" update="messageLists" name="updateStatusForm">
           <g:textArea name="message" value="" id="messageArea" /><br/> <br/>
           <g:submitButton name="Update Status" /></div> 
        </g:formRemote>
    </div>
    <div id="messageLists">
        <g:render template="messages" collection="${messages}" var="message"/>
    </div>
</body>
</html>