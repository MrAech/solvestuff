
# Gauss Elimination endpoint

```bash
curl -X POST http://localhost:8080/direct/gauss-elimination \
  -H "Content-Type: application/json" \
  -d '{"matrix": [[3,2],[1,2]], "vector": [5,3]}' | jq
```



# Gauss Jordan endpoint 

```bash 
curl -X POST http://localhost:8080/direct/gauss-jordan \
  -H "Content-Type: application/json" \
  -d '{"matrix": [[2,1],[5,7]], "vector": [11,13]}' | jq
```




```
```
```
```


```
```
```
