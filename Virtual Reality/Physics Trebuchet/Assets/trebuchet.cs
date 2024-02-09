using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Trebuchet : MonoBehaviour
{
    public Rigidbody weight;
    public GameObject cannonball;
    public GameObject arm;
    public GameObject mainArm;
    public GameObject weightObject;
    public GameObject slingArm;

    private bool trebuchetLoaded = false;
    private float movingSpeed = 3.5f;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        float horizontalInput = Input.GetAxis("Horizontal");
        float verticalInput = Input.GetAxis("Vertical");
        float horizontalMovement = horizontalInput * movingSpeed * Time.deltaTime;
        float verticalMovement = verticalInput * movingSpeed * Time.deltaTime;
        transform.Translate(new Vector3(horizontalMovement, 0f, verticalMovement));

        if (Input.GetKeyDown(KeyCode.Space))
        {
            weight.isKinematic = false;
        }

        if (Input.GetKeyUp(KeyCode.Space))
        {
            HingeJoint hingeToDestroy = cannonball.GetComponent<HingeJoint>();
            Destroy(hingeToDestroy);
            
            WeightScript scriptToDisable = cannonball.GetComponent<WeightScript>();
            if (scriptToDisable != null)
            {
                scriptToDisable.enabled = false;
            }
        }

        if (Input.GetKeyDown(KeyCode.R))
        {
            StartCoroutine(ResetTrebuchet());
        }
    }

    void LoadTrebuchet()
    {
        weight.isKinematic = false;
        trebuchetLoaded = true;
    }

    void LaunchCannonBall()
    {
        HingeJoint hingeToDestroy = cannonball.GetComponent<HingeJoint>();
        Destroy(hingeToDestroy);
        trebuchetLoaded = false;
    }

    IEnumerator ResetTrebuchet()
    {
        // Move the weight to its initial position and make it kinematic
        yield return new WaitForSeconds(0.5f);
        weight.isKinematic = true;
        weightObject.transform.position = new Vector3(197.59f, -1.45f, 17.0152f);

        // Wait for another short duration
        yield return new WaitForSeconds(1f);

        // Move the arm to its initial position and rotation
        mainArm.transform.rotation = Quaternion.Euler(-35f, 90f, 0f);
        mainArm.transform.position = new Vector3(-1.313293f, 4.324162f, 0);

        // Wait for a short duration
        yield return new WaitForSeconds(1f);

        // Recreate the HingeJoint component for the cannonball
        HingeJoint hingeJoint = cannonball.AddComponent<HingeJoint>();
        Rigidbody slingArmRigidbody = slingArm.GetComponent<Rigidbody>();

        // Ensure the SlingArm has a Rigidbody component
        if (slingArmRigidbody == null)
        {
            slingArmRigidbody = slingArm.AddComponent<Rigidbody>();
            slingArmRigidbody.isKinematic = true; // Make it kinematic to avoid undesired physics interactions
        }

        hingeJoint.connectedBody = slingArmRigidbody;

        // Wait for a short duration
        yield return new WaitForSeconds(1f);

        // Move the cannonball to its initial position
        float yOffset = 1f; // Adjust the offset as needed
        Vector3 initialBallPosition = slingArm.transform.position + new Vector3(0, yOffset, 0);
        cannonball.GetComponent<Rigidbody>().MovePosition(initialBallPosition);

        // Mark the trebuchet as not loaded
        trebuchetLoaded = false;
    }
}
