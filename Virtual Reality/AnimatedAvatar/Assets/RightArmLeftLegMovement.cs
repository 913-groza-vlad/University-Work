using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RightArmLeftLegMovement : MonoBehaviour
{
    private float velocity = 30.0f;
    private float minDegree = -30.0f;
    private float maxDegree = 30.0f;
    private float angle = 0.0f;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        var x = angle + velocity * Time.deltaTime;
        if (x < minDegree || x > maxDegree)
        {
            velocity *= -1;
        }
        angle += velocity * Time.deltaTime;
        transform.localRotation = Quaternion.AngleAxis(x, Vector3.right);
    }
}
