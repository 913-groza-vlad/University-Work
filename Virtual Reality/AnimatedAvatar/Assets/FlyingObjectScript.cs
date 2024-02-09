using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FlyingObjectScript : MonoBehaviour
{
    private float objectY;
    private float radius;
    private float movingSpeed = 0.75f;
    // Start is called before the first frame update
    void Start()
    {
        var position = transform.position;
        objectY = position.y;
        radius = Mathf.Sqrt(position.x * position.x + position.z * position.z);
    }

    // Update is called once per frame
    void Update()
    {
        var time = Time.time * movingSpeed;
        var x = radius * Mathf.Sin(time);
        var z = radius * Mathf.Cos(time);

        // Set the new position
        var transform1 = transform;
        var position = new Vector3(x, objectY, z);
        transform1.position = position;

        var lookAtPosition = new Vector3(0f, objectY, 0f);
        transform.LookAt(lookAtPosition);
    }
}
