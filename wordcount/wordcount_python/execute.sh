hadoop jar /path/to/hadoop-streaming.jar \
    -input /path/to/input \
    -output /path/to/output \
    -mapper mapper.py \
    -reducer reducer.py \
    -file mapper.py \
    -file reducer.py

