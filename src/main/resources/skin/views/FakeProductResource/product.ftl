<!DOCTYPE html>
<html lang="en">
<head>
<title>${product.title}</title>
<#include "views/FakeProductResource/head.ftl" />
</head>
<body>
    <#include "views/FakeProductResource/header.ftl" />
    <div class="body">
      <div class="product" id="${product.reference?c}">
          <a href="${product.url}">
            <h2>
              #${product.reference?c} - ${product.title}
            </h2>
          </a>
          <p>${product.description}</p>
      </div>
    </div>
    <#include "views/FakeProductResource/footer.ftl" />
</body>
</html>