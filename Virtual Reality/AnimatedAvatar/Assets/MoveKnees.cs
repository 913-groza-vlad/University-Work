using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MoveKnees : MonoBehaviour
{
    private float minDegree = -30.0f;
    private float maxDegree = 0.0f;
    private float velocity = 30.0f;
    private float angle = 0.0f;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        var newAngle = angle + velocity * Time.deltaTime;
        if (newAngle > maxDegree || newAngle < minDegree)
        {
            velocity *= -1;
        }
        angle += velocity * Time.deltaTime;
        transform.localRotation = Quaternion.AngleAxis(angle, Vector3.right);
    }
}
