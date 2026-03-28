
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


# Jacobi endpoint
```bash
curl -X POST http://localhost:8080/api/linear/jacobi \
  -H "Content-Type: application/json" \
  -d '{"matrix": [[10,2],[1,5]], "vector": [7,6], "iterationConfig": {"tolerance": 1e-6, "maxIterations": 25}}' | jq
```
```
```



# Gauss-Seidel endpoint
```bash
curl -X POST http://localhost:8080/iterative/seidel \
  -H "Content-Type: application/json" \
  -d '{"matrix": [[10,2],[1,5]], "vector": [7,6], "iterationConfig": {"tolerance": 1e-6, "maxIterations": 25}}' | jq
```
