.PHONY: help

help:
	@echo Public targets:
	@grep -E '^[^_#][^_][a-zA-Z_-]+:.*?## .*$$' $(firstword $(MAKEFILE_LIST)) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-20s\033[0m %s\n", $$1, $$2}'

deploy-web: ## Deploy the web application.
	cd web-app && \
		aws s3 sync build s3://covourier-test/ --delete --acl public-read