<!DOCTYPE html>
<html lang="en">
<head>
<title>Products</title>
<#include "views/FakeProductResource/head.ftl" />
</head>
<body>
    <#include "views/FakeProductResource/header.ftl" />
    <div class="body">
      <ul>
          <#list products as product>
          <li>
              <div class="product" id="${product.reference?c}">
                  <a href="${product.url}">
                    <h2>
                      #${product.reference?c} - ${product.title}
                    </h2>
                  </a>
                  <p>${product.description}</p>
              </div>
          </li>
          </#list>
      </ul>
    </div>
    <#include "views/FakeProductResource/footer.ftl" />
</body>
</html>