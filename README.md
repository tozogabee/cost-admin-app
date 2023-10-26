# Cost Administartion Application

## Description of the Cost Administartion Application
The user can organize his spends by categories and can check the statistics how many spends were on the corresponding month.

Welcome on us.

### 1. For QA Engineers

<table>
    <tr>
        <th>What</th>
        <th>URL</th>
        <th>Other information</th>
    </tr>
    <tr>
        <td>H2-console</td>
        <td>http://localhost:8080/h2-console</td>
        <td>login name: sa</td>
    </tr>
    <tr>
        <td rowspan="9">REST API ENDPOINT</td>
        <td>/api/addSpent</td>
        <td>POST request, use the json files to test in the resources/json</td>
    </tr>
    <tr>
        <td>/api/all</td>
        <td>GET request - listing all costs in the database.</td>
    </tr>
    <tr>
        <td>/api/stats/categories/all"</td>
        <td>GET request - listing all costs by categories</td>
    </tr>
    <tr>
        <td>/api/stats/categories/all"</td>
        <td>GET request - listing all costs by categories</td>
    </tr>
    <tr>
        <td>/api/stats/categories</td>
        <td>GET request - statistics sum of prices for added category</td>
    </tr>
    <tr>
        <td>/api/stats?fromDate={fromDate}&toDate={toDate}</td>
        <td>GET request - statistics sum of prices per category per month</td>
    </tr>
    <tr>
        <td>/api/delete/{id}</td>
        <td>DELETE request - remove an cost by ID.</td>
    </tr>
    <tr>
        <td>/api/update</td>
        <td>UPDATE request - update an entity if this is exists.</td>
    </tr>
    <tr>
        <td>/api/update</td>
        <td>UPDATE request - update an entity if this is exists.</td>
    </tr>

</table>

## JSON example to POST
{
    "summary": "rent flat",
    "category": "housing",
    "paid": "2023-10-26T00:00",
    "sum": 789,
    "currency": "HUF"
}

## JSON example to PUT
{
"id":1
"summary": "rent flat",
"category": "housing",
"paid": "2023-10-26T00:00",
"sum": 1000,
"currency": "HUF"
}



To testing use the Postman or Insomnia application.
