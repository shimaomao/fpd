<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include-angular/taglib.jsp"%>
<html ng-app="app">
<head>
	<%@ include file="/WEB-INF/views/include-angular/head.jsp"%>
</head>
<body class="container" ng-controller="DemoController">
    <div class="row">
        <h1>angular-form-builder</h1>
        <hr/>

        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Builder</h3>
                </div>
                <div fb-builder="default"></div>
                <div class="panel-footer">
                    <div class="checkbox">
                        <label><input type="checkbox" ng-model="isShowScope" />
                            Show scope
                        </label>
                    </div>
                    <pre ng-if="isShowScope">{{form}}</pre>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div fb-components></div>
        </div>
    </div>

    <div class="row">
        <h2>Form</h2>
        <hr/>
        <form class="form-horizontal">
            <div ng-model="input" fb-form="default" fb-default="defaultValue"></div>
            <div class="form-group">
                <div class="col-md-8 col-md-offset-4">
                    <input type="submit" ng-click="submit()" class="btn btn-default"/>
                </div>
            </div>
        </form>
        <div class="checkbox">
            <label><input type="checkbox" ng-model="isShowScope" ng-init="isShowScope=true" />
                Show scope
            </label>
        </div>
        <pre ng-if="isShowScope">{{input}}</pre>
    </div>

    <div class="row">
        <div class="col-md-12 footer"></div>
    </div>
</body>
</html>