<%@ include file="../template.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
    function queryBranches(pageNumber) {

        $.ajax({
            url: "/lms/branches/query",
            data: {
                q: $('#searchInput').val() || "",
                pageNo: ''+pageNumber
            }
        }).done(fillTable);
    }

    function fillTable(data) {

        if(data.alert !== null) {
            $('#branchAlert').html(data.alert)
            $('#branchAlert').hide(2000);
        }

        var pageNo = data.pageNo;
        var content = "<table class='table'>";

        content += "<tr><th>#</th><th>Branch Name</th><th>Branch Address</th></tr>";

        for(var i=0;i<data.branches.length;i++) {
            content += "<tr>";
            content += "<td>" + ((i + 1) + ((pageNo - 1) * 10)) + "</td>";
            content += "<td><a href='#' data-toggle='modal' data-target='#editBranchModal' ";
            content += "onclick='getBranchDetails("+data.branches[i].branchId+")'>";
            content += data.branches[i].branchName + "</a></td>";

            content += "<td>"+data.branches[i].branchAddress+"</td>";
            content += "</tr>";
        }

        content += "</table>";


        var size = data.fetchSize;
        var pages = 1;

        if(size % 10 > 1) {
            pages = (size/10) + 1;
        } else {
            pages = size/10;
        }

        content += "<nav aria-label='Page navigation'>";
        content += "<ul class='pagination'>";

        for(var i=1;i<=pages;i++) {
            content += "<li><a href='javascript:queryBranches("+i+")'>"+i+"</a></li>";
        }

        content += "</ul></nav>";

        $("#branchesTable").html(content);
    }

    function getBranchDetails(branchId) {
        $.ajax({
            url: "/lms/branches/view",
            data: {
                branchId: branchId
            }
        }).done(function (response) {
            $('.editBranchModalBody').html(response);
        });
    }

    queryBranches(1);

</script>

<style>
    .inline {
        display: inline;
    }
    .right {
        float: right;
    }
</style>

<div class="container theme-showcase" role="main">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">
            <div>
                <h2 class="inline">Branches</h2>
                <div class="inline right input-group input-group-lg">
                    <!-- <span class="input-group-addon" id="sizing-addon1"> -->
                    <input type="text" class="form-control" placeholder="Branch Name"
                           name="searchString" id="searchInput" onkeydown="queryBranches(1)">
                </div>
            </div><br><br>
        </div>
        <div class="panel-body">
            <a href='#' data-toggle='modal' data-target='#editBranchModal' onclick="getBranchDetails(0)">Add New Branch</a>
            <div id="bookAlert"></div>
        </div>
        <div id="branchesTable"></div>

    </div>
</div>
</div>

<div class="modal fade bs-example-modal-lg" id="editBranchModal" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="editBranchModalBody">

                </div>
            </div>
        </div>
</div>